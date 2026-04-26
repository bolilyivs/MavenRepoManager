package ru.bolilyivs.dependency.manager;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

public interface MavenDependencyFinder {
    Artefact resolve(Repository repository, ArtefactId id);
}
