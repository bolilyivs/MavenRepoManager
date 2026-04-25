package ru.bolilyivs.dependency.manager.model.artefact;

import java.nio.file.Path;

public record ArtefactFile(
        String filename,
        ArtefactFileType type,
        Path path
) {
    public static ArtefactFile of(final String filename, final ArtefactFileType type, final String artefactPath) {
        String path = "%s/%s".formatted(artefactPath, filename);
        return new ArtefactFile(filename, type, Path.of(path));
    }

    public static ArtefactFile of(final String filename, final ArtefactFileType type, final ArtefactMetaData metaData) {
        String path = "%s/%s".formatted(metaData.getPath(), filename);
        return new ArtefactFile(filename, type, Path.of(path));
    }

    public static ArtefactFile of(final String sourcePath) {
        Path path = Path.of(sourcePath);
        String fileName = path.getFileName().toString();
        return new ArtefactFile(
                fileName,
                ArtefactFileType.ofFileName(fileName),
                path
        );
    }
}
