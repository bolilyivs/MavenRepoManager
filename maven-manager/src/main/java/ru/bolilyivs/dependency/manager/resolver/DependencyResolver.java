package ru.bolilyivs.dependency.manager.resolver;

import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;

public interface DependencyResolver {
    Dependency resolver(ArtefactMetaData metaData);
}
