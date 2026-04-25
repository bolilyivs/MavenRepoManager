package ru.bolilyivs.server.rest;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.server.types.files.SystemFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenArtefactDownloader;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.server.config.AppConfig;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.service.RepoService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller("/repo")
@RequiredArgsConstructor
@Tag(name = "RepoController")
public class RepoController {

    private final AppConfig appConfig;
    private final MavenArtefactDownloader mavenArtefactDownloader;
    private final RepoService repoService;

    @Get(uri = "/{repoName}/{otherPath:.*}")
    public SystemFile get(@PathVariable String repoName, String otherPath) {
        Path path = Paths.get(appConfig.getRootRepoDir(), repoName, otherPath);
        if (!Files.exists(path)) {
            RepoDto repoDto = repoService.get(repoName);
            Repository repository = new Repository(repoDto.name(), repoDto.url());
            ArtefactFile artefactFile = ArtefactFile.of(otherPath);
            mavenArtefactDownloader.downloadArtefactToFile(repository, artefactFile);
        }
        return new SystemFile(path.toFile(), MediaType.ALL_TYPE);
    }
}
