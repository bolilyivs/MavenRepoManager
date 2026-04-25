package ru.bolilyivs.dependency.manager;

import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;

public interface MavenDependencyFinder {
    Dependency find(IvyConfig ivyConfig, ArtefactMetaData metaData);
}
