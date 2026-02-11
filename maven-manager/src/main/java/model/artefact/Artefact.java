package model.artefact;

import java.util.Collection;

public record Artefact(
        ArtefactMetaData metaData,
        Collection<ArtefactFile> files
) {
}
