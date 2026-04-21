package ru.bolilyivs.server.service;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.server.data.dto.RepoDto;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    private final HttpClient httpClient;
    private final MavenDependencyFinder mavenDependencyFinder;
    private final RepoService repoService;

    @Override
    public void downloadArtifact(String repoName, String dependecyString) {
        CompletableFuture.runAsync(() -> {
            asyncDownloadArtifact(repoName, dependecyString);
        });
    }

    private void asyncDownloadArtifact(String repoName, String dependecyString) {
        RepoDto repoDto = repoService.get(repoName);
        Dependency dependency = mavenDependencyFinder.find(repoDto.url(), repoDto.name(), ArtefactMetaData.of(dependecyString));
        Set<Dependency> dependencySet = dependency.getFlatListDependencies();
        dependencySet.forEach(dep -> tryDownload(repoDto, dep));
    }

    @SneakyThrows
    private void tryDownload(RepoDto repoDto, Dependency dependency) {
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                download(repoDto, dependency);
            } catch (Exception e) {
                log.error("Download artefact failed: attempt {}", i + 1, e);
            }
        }
    }

    @SneakyThrows
    private void download(RepoDto repoDto, Dependency dependency) {
        String path = dependency.artefactMetaData().getPath();
        String filename = dependency.artefactMetaData().getFilename(ArtefactFileType.JAR);
        Path destFile = Paths.get("/home/shenlong/repos/",
                repoDto.name(),
                path,
                filename
        );

        if (destFile.toFile().exists()) {
            return;
        }

        URI uri = UriBuilder.of(repoDto.url())
                .path(path)
                .path(filename)
                .build();

        HttpRequest<?> req = HttpRequest.GET(uri);

        byte[] file = httpClient
                .toBlocking()
                .retrieve(req, byte[].class);


        Files.createDirectories(destFile.getParent());
        Files.write(destFile, file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
