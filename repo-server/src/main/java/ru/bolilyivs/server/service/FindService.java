package ru.bolilyivs.server.service;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

public interface FindService {

    Artefact findArtefactWithDependencies(String repoName, String dependecyString);

    Artefact findArtefact(String repoName, String dependecyString);

    Artefact findArtefact(Repository repository, ArtefactId metaData);

}
