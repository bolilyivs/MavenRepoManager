package ru.bolilyivs.server.service;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenArtefactFileFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.server.data.dto.RepoDto;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class FindServiceImpl implements FindService {

    private final MavenDependencyFinder mavenDependencyFinder;
    private final MavenArtefactFileFinder mavenArtefactFileFinder;
    private final RepoService repoService;

    @Override
    public Artefact findArtefactWithDependencies(String repoName, String dependecyString) {
        RepoDto repoDto = repoService.get(repoName);
        Repository repository = new Repository(repoDto.name(), repoDto.url());
        return mavenDependencyFinder.resolve(repository, ArtefactId.of(dependecyString));
    }

    @Override
    public Artefact findArtefact(String repoName, String dependecyString) {
        RepoDto repoDto = repoService.get(repoName);
        Repository repository = new Repository(repoDto.name(), repoDto.url());
        return findArtefact(repository, ArtefactId.of(dependecyString));
    }

    @Override
    public Artefact findArtefact(Repository repository, ArtefactId metaData) {
        List<ArtefactFile> artefactFiles = mavenArtefactFileFinder.find(repository, metaData);
        return Artefact.ofArtefactFiles(metaData, artefactFiles);
    }
}
