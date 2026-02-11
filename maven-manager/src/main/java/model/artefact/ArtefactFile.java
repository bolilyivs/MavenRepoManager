package model.artefact;

public record ArtefactFile(
        String filename,
        ArtefactFileType type,
        String url
) {
}
