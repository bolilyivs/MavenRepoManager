package ru.bolilyivs.server.service;

import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.bolilyivs.dependency.manager.MavenArtefactDownloader;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.ivy.impl.IvyConfigImpl;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.server.config.AppConfig;
import ru.bolilyivs.server.data.dto.RepoDto;

import java.nio.file.Files;
import java.nio.file.Path;
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
    private final MavenArtefactDownloader mavenArtefactDownloader;
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
        Dependency dependency = mavenDependencyFinder.resolve(ivyConfig, ArtefactMetaData.of(dependecyString));
        Set<Dependency> dependencySet = dependency.getFlatListDependencies();
        dependencySet.forEach(dep -> tryDownloadArtefact(repoDto, dep));
        log.info("Downloaded done!");
    }

    @SneakyThrows
    private void tryDownloadArtefact(RepoDto repoDto, Dependency dependency) {
        for (int i = 0; i < 3; i++) {
            try {
                downloadArtefact(repoDto, dependency);
                return;
            } catch (Exception e) {
                log.error("Download artefact failed: attempt {}", i + 1, e);
                TimeUnit.MICROSECONDS.sleep(500);
            }
        }
    }

    @SneakyThrows
    private void downloadArtefact(RepoDto repoDto, Dependency dependency) {
        Repository repository = new Repository(repoDto.name(), repoDto.url());

        Artefact artefact = mavenArtefactFinder.find(repository, dependency.artefactMetaData());
        artefact.files()
                .stream()
                .filter(artefactFile -> !Files.exists(Path.of(appConfig.getRootRepoDir(), repoDto.name(), artefactFile.path().toString())))
                .forEach(file -> mavenArtefactDownloader.downloadArtefactToFile(repository, file));
        log.info("{} is downloaded", artefact.metaData());
    }
}
