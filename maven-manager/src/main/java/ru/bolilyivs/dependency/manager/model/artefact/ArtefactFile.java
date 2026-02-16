package ru.bolilyivs.dependency.manager.model.artefact;

public record ArtefactFile(
        String filename,
        ArtefactFileType type,
        String path
) {
    public static ArtefactFile of(final String filename, final ArtefactFileType type, final String artefactPath) {
        String path = "%s/%s".formatted(artefactPath, filename);
        return new ArtefactFile(filename, type, path);
    }
}
