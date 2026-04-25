package ru.bolilyivs.dependency.manager;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

public interface MavenArtefactFinder {
    Artefact find(Repository repository, ArtefactMetaData metaData);
}
