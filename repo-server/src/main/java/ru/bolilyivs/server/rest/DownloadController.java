package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.service.DownloadService;

@Controller("/api/v1/repo")
@RequiredArgsConstructor
@Tag(name = "DownloadController")
public class DownloadController {

    private final DownloadService downloadService;

    @Get(uri = "{repoName}/download/dependencies")
    public void dependencies(@PathVariable String repoName, @Parameter String dependecyString) {
        downloadService.downloadArtifactWithDependencies(repoName, dependecyString);
    }

    @Get(uri = "{repoName}/download/artefact")
    public void artefact(@PathVariable String repoName, @Parameter String dependecyString) {
        downloadService.downloadArtifact(repoName, dependecyString);
    }
}
