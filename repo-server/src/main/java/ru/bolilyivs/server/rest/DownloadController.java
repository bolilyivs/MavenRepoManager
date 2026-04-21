package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.service.DownloadService;

@Controller("/api/v1/download/repo")
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;

    @Get(uri = "/artefact")
    public void artefact(@Parameter String repoName, @Parameter String dependecyString) {
        downloadService.downloadArtifact(repoName, dependecyString);
    }
}
