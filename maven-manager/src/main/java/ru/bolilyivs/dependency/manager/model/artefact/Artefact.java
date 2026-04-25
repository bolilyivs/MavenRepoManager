package ru.bolilyivs.dependency.manager.model.artefact;

import java.util.List;

public record Artefact(
        ArtefactMetaData metaData,
        List<ArtefactFile> files
) {
}
