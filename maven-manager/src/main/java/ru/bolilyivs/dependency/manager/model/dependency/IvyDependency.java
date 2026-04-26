package ru.bolilyivs.dependency.manager.model.dependency;

import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.List;

public record IvyDependency(ArtefactId id, List<ArtefactId> dependencies) {
}
