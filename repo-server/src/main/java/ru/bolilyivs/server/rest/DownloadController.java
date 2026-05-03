package ru.bolilyivs.server.rest;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.ArtefactRequestDTO;
import ru.bolilyivs.server.service.DownloadService;

@Controller("/api/v1/repo")
@RequiredArgsConstructor
@Tag(name = "DownloadController", description = "Скачивание артефактов")
public class DownloadController {

    private final DownloadService downloadService;

    @Post(uri = "{repoName}/download/artefact/dependencies")
    @Operation(summary = "Закачать артефакт с зависимостями",
            description = "Закачать в репозиторий артефакт со всеми зависимостями"
    )
    public void artefactWithDependencies(
            @PathVariable String repoName,
            @Body ArtefactRequestDTO artefactRequestDTO) {
        downloadService.downloadArtifactWithDependencies(repoName, artefactRequestDTO.moduleDependency());
    }

    @Post(uri = "{repoName}/download/artefact")
    @Operation(summary = "Закачать артефакт без зависимостей",
            description = "Закачать в репозиторий артефакт со всеми зависимостями"
    )
    public void artefact(@PathVariable String repoName,
                         @Body ArtefactRequestDTO artefactRequestDTO) {
        downloadService.downloadArtifact(repoName, artefactRequestDTO.moduleDependency());
    }
}
