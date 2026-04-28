package ru.bolilyivs.server.service;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenManager;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.server.data.dto.RepoDto;

import java.util.List;
import java.util.Objects;

@Singleton
@RequiredArgsConstructor
public class FindServiceImpl implements FindService {

    private final MavenManager mavenManager;
    private final RepoService repoService;
    private final ArtefactService artefactService;

    @Override
    public Artefact findArtefactWithDependenciesAndFiles(String repoName, String dependecyString) {
        return artefactService.findById(dependecyString).orElseGet(() -> {
            Repository repository = findRepository(repoName);
            Artefact artefact = mavenManager.findArtefactWithFilesAndDependencies(repository, ArtefactId.of(dependecyString));
            if (Objects.nonNull(artefact)) {
                artefactService.save(artefact);
            }
            return artefact;
        });
    }

    @Override
    public Artefact findArtefactWithDependencies(String repoName, String dependecyString) {
        return artefactService.findByIdWithoutFiles(dependecyString).orElseGet(() -> {
            Repository repository = findRepository(repoName);
            return mavenManager.resolveDependency(repository, ArtefactId.of(dependecyString));
        });
    }

    @Override
    public Artefact findArtefactWithFiles(String repoName, String dependecyString) {
        return artefactService.findByIdWithoutDependecies(dependecyString).orElseGet(() -> {
            Repository repository = findRepository(repoName);
            return mavenManager.findArtefactWithFiles(repository, ArtefactId.of(dependecyString));
        });
    }

    @Override
    public Artefact findArtefactWithFiles(Repository repository, ArtefactId metaData) {
        return mavenManager.findArtefactWithFiles(repository, metaData);
    }

    @Override
    public List<String> findArtefactId(String repoName, String groupId) {
        Repository repository = findRepository(repoName);
        return mavenManager.listArtefactId(repository, groupId);
    }

    @Override
    public List<String> findVersion(String repoName, String groupId, String artefactId) {
        Repository repository = findRepository(repoName);
        return mavenManager.listVersion(repository, groupId, artefactId);
    }

    private Repository findRepository(String repoName) {
        RepoDto repoDto = repoService.get(repoName);
        return new Repository(repoDto.name(), repoDto.url());
    }
}
