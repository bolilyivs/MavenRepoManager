package ru.bolilyivs.server.service;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.server.data.dto.RepoDto;

@Singleton
@RequiredArgsConstructor
public class FindServiceImpl implements FindService {

    private final MavenDependencyFinder mavenDependencyFinder;
    private final MavenArtefactFinder mavenArtefactFinder;
    private final RepoService repoService;

    @Override
    public Dependency findDependencies(String repoName, String dependecyString) {
        RepoDto repoDto = repoService.get(repoName);
        Repository repository = new Repository(repoDto.name(), repoDto.url());
        return mavenDependencyFinder.resolve(repository, ArtefactMetaData.of(dependecyString));
    }

    @Override
    public Artefact findArtefact(String repoName, String dependecyString) {
        RepoDto repoDto = repoService.get(repoName);
        Repository repository = new Repository(repoDto.name(), repoDto.url());
        return findArtefact(repository, ArtefactMetaData.of(dependecyString));
    }

    @Override
    public Artefact findArtefact(Repository repository, ArtefactMetaData metaData) {
        return mavenArtefactFinder.find(repository, metaData);
    }
}
