package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.server.data.dto.DependencyDto;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.service.RepoService;

@Controller("/api/v1/search/repo")
@RequiredArgsConstructor
@Tag(name = "SearchController")
public class SearchController {

    private final MavenDependencyFinder mavenDependencyFinder;
    private final RepoService repoService;

    @Get(uri = "/dependency") // (2)
    public DependencyDto index(@Parameter String repoName, @Parameter String dependecyString) {
        RepoDto repoDto = repoService.get(repoName);
        Dependency dependency = mavenDependencyFinder.find(repoDto.url(), repoDto.name(), ArtefactMetaData.of(dependecyString));
        return DependencyDto.ofDependency(dependency);
    }
}
