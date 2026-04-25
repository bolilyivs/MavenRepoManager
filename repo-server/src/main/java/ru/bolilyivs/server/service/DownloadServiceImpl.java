package ru.bolilyivs.server.service;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.ivy.impl.IvyConfigImpl;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.server.config.AppConfig;
import ru.bolilyivs.server.data.dto.RepoDto;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    private final HttpClient httpClient;
    private final MavenDependencyFinder mavenDependencyFinder;
    private final MavenArtefactFinder mavenArtefactFinder;
    private final RepoService repoService;
    private final AppConfig appConfig;

    @Override
    public void downloadArtifact(String repoName, String dependecyString) {
        CompletableFuture.runAsync(() -> {
            asyncDownloadWithDependency(repoName, dependecyString);
        });
    }

    private void asyncDownloadWithDependency(String repoName, String dependecyString) {
        RepoDto repoDto = repoService.get(repoName);
        IvyConfig ivyConfig = IvyConfigImpl.of(
                repoDto.name(),
                repoDto.url(),
                appConfig.getCacheDir()
        );
        Dependency dependency = mavenDependencyFinder.find(ivyConfig, ArtefactMetaData.of(dependecyString));
        Set<Dependency> dependencySet = dependency.getFlatListDependencies();
        dependencySet.forEach(dep -> tryDownloadArtefact(repoDto, dep));
    }

    @SneakyThrows
    private void tryDownloadArtefact(RepoDto repoDto, Dependency dependency) {
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(100);
                downloadArtefact(repoDto, dependency);
            } catch (Exception e) {
                log.error("Download artefact failed: attempt {}", i + 1, e);
            }
        }
    }

    @SneakyThrows
    private void downloadArtefact(RepoDto repoDto, Dependency dependency) {
        Artefact artefact = mavenArtefactFinder.find(repoDto.url(), dependency.artefactMetaData());
        List<String> artefactPath = artefact.files().values().stream().map(ArtefactFile::path).toList();
        artefactPath.forEach(path -> downloadFile(repoDto, path));
    }

    @SneakyThrows
    private void downloadFile(RepoDto repoDto, String artefactPath) {
        Path destFile = Paths.get(appConfig.getRootRepoDir(),
                repoDto.name(),
                artefactPath
        );

        if (destFile.toFile().exists()) {
            return;
        }

        URI uri = UriBuilder.of(repoDto.url())
                .path(artefactPath)
                .build();

        HttpRequest<?> req = HttpRequest.GET(uri);

        byte[] file = httpClient
                .toBlocking()
                .retrieve(req, byte[].class);


        Files.createDirectories(destFile.getParent());
        Files.write(destFile, file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
