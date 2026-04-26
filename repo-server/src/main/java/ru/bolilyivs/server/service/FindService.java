package ru.bolilyivs.server.service;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.List;

public interface FindService {

    Artefact findArtefactWithDependenciesAndFiles(String repoName, String dependecyString);

    Artefact findArtefactWithDependencies(String repoName, String dependecyString);

    Artefact findArtefactWithFiles(String repoName, String dependecyString);

    Artefact findArtefactWithFiles(Repository repository, ArtefactId metaData);

    List<String> findArtefactId(String repoName, String groupId);

    List<String> findVersion(String repoName, String groupId, String artefactId);

}
