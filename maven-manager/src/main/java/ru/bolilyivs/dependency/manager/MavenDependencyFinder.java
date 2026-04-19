package ru.bolilyivs.dependency.manager;

import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;

public interface MavenDependencyFinder {
    Dependency find(String repoBaseUrl, String name, ArtefactMetaData metaData);
}
