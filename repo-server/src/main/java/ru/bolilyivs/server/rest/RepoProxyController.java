package ru.bolilyivs.server.rest;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.server.types.files.SystemFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.service.RepoProxyService;

import java.nio.file.Path;

@Controller("/repo")
@RequiredArgsConstructor
@Tag(name = "RepoProxyController", description = "Прокси для зеркалирования удалённого репозитория")
public class RepoProxyController {

    private final RepoProxyService repoProxyService;

    @Get(uri = "/{repoName}/{otherPath:.*}")
    @Operation(summary = "Прокси для зеркалирования удалённого репозитория",
            description = "Прокси для зеркалирования удалённого репозитория"
    )
    public SystemFile get(
            @Parameter(description = "Название репозитория")
            @PathVariable
            String repoName,
            @Parameter(description = "Относительный путь к артефакту")
            @PathVariable
            String otherPath
    ) {
        Path path = repoProxyService.getFile(repoName, otherPath);
        return new SystemFile(path.toFile(), MediaType.ALL_TYPE);
    }
}
