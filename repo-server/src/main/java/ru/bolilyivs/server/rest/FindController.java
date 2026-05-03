package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.ArtefactDto;
import ru.bolilyivs.server.service.FindService;

@Controller("/api/v1/repo/")
@RequiredArgsConstructor
@Tag(name = "FindController", description = "Поиск артефактов")
public class FindController {

    private final FindService findService;

    @Get(uri = "{repoName}/find/artefact")
    @Operation(summary = "Найти артефакт с зависимостями и файлами",
            description = "Найти артефакт со всеми зависимостями и файлами"
    )
    public ArtefactDto findArtefact(
            @PathVariable String repoName,
            @Parameter String moduleDependency) {
        return ArtefactDto.of(findService.findArtefactWithDependenciesAndFiles(repoName, moduleDependency));
    }

    @Get(uri = "{repoName}/find/artefact/files")
    @Operation(summary = "Найти артефакт с файлами",
            description = "Найти артефакт без зависимостями, но с файлами"
    )
    public ArtefactDto findArtefactWithFiles(
            @PathVariable String repoName,
            @Parameter String moduleDependency) {
        return ArtefactDto.of(findService.findArtefactWithFiles(repoName, moduleDependency));
    }
}
