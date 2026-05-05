package ru.bolilyivs.dependency.manager.service;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.List;

public interface MavenArtefactFileFinder {
    List<ArtefactFile> find(Repository repository, ArtefactId id);
}
