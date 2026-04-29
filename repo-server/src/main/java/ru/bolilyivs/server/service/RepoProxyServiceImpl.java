package ru.bolilyivs.server.service;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.service.MavenArtefactDownloader;
import ru.bolilyivs.server.config.AppConfig;
import ru.bolilyivs.server.data.dto.RepoDto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
@RequiredArgsConstructor
public class RepoProxyServiceImpl implements RepoProxyService {

    private final AppConfig appConfig;
    private final MavenArtefactDownloader mavenArtefactDownloader;
    private final RepoService repoService;
    private final DownloadService downloadService;

    @Override
    public Path getFile(String repoName, String artefactPath) {
        Path path = Paths.get(appConfig.getRootRepoDir(), repoName, artefactPath);
        if (Files.exists(path)) {
            return path;
        }

        RepoDto repoDto = repoService.get(repoName);
        Repository repository = new Repository(repoDto.name(), repoDto.url());
        ArtefactFile artefactFile = ArtefactFile.of(artefactPath);
        mavenArtefactDownloader.downloadArtefactToFile(repository, artefactFile);
        return path;
    }

}
