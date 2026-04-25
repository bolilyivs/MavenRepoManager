package ru.bolilyivs.server.service;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;

public interface FindService {

    Dependency findDependencies(String repoName, String dependecyString);

    Artefact findArtefact(String repoName, String dependecyString);

    Artefact findArtefact(Repository repository, ArtefactMetaData metaData);

}
