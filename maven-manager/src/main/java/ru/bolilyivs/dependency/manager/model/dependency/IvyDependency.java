package ru.bolilyivs.dependency.manager.model.dependency;

import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

import java.util.List;

public record IvyDependency(ArtefactMetaData metaData, List<ArtefactMetaData> dependenies) {
}
