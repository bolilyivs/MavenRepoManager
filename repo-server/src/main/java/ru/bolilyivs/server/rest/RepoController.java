package ru.bolilyivs.server.rest;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.server.types.files.SystemFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.config.AppConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller("/repo")
@RequiredArgsConstructor
@Tag(name = "RepoController")
public class RepoController {

    private final AppConfig appConfig;

    @Get(uri = "/{repoName}/{otherPath:.*}")
    public SystemFile get(@PathVariable String repoName, String otherPath) {
        Path path = Paths.get(appConfig.getRootRepoDir(), repoName, otherPath);
        return new SystemFile(path.toFile(), MediaType.ALL_TYPE);
    }
}
