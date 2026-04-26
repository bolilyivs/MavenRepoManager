package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.ArtefactDto;
import ru.bolilyivs.server.data.dto.DependencyDto;
import ru.bolilyivs.server.service.FindService;

@Controller("/api/v1/search/")
@RequiredArgsConstructor
@Tag(name = "SearchController")
public class FindController {

    private final FindService findService;

    @Get(uri = "{repoName}/find/dependencies") // (2)
    public DependencyDto findDependencies(@PathVariable String repoName, @Parameter String dependecyString) {
        return DependencyDto.ofDependency(findService.findArtefactWithDependencies(repoName, dependecyString));
    }

    @Get(uri = "{repoName}/find/artefact") // (2)
    public ArtefactDto findArtefact(@PathVariable String repoName, @Parameter String dependecyString) {
        return ArtefactDto.of(findService.findArtefact(repoName, dependecyString));
    }
}
