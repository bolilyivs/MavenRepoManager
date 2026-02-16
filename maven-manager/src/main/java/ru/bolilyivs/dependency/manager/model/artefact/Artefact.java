package ru.bolilyivs.dependency.manager.model.artefact;

import java.util.Map;

public record Artefact(
        ArtefactMetaData metaData,
        Map<ArtefactFileType, ArtefactFile> files
) {
    public ArtefactFile getFile(ArtefactFileType fileType) {
        return files.getOrDefault(fileType, null);
    }
}
