package ru.bolilyivs.dependency.manager.model.artefact;

import java.nio.file.Path;

public record ArtefactId(
        String groupId,
        String artifactId,
        String version
) {
    public static ArtefactId of(String artefactOneString) {
        String[] metaData = artefactOneString.split(":");
        return new ArtefactId(
                metaData[0],
                metaData[1],
                metaData[2]
        );
    }

    public static ArtefactId ofArtefactFile(final ArtefactFile artefactFile) {
        return ofRelativeFilePath(artefactFile.path());
    }

    public static ArtefactId ofRelativeFilePath(final Path relativeFilePath) {
        Path path = relativeFilePath.getParent();
        return ofRelativeFolderPath(path);
    }

    public static ArtefactId ofRelativeFolderPath(final Path relativeFolderPath) {
        Path path = relativeFolderPath;
        String version = path.getFileName().toString();
        path = path.getParent();
        String artifactId = path.getFileName().toString();
        String groupId = path.getParent().toString();

        return new ArtefactId(
                groupId,
                artifactId,
                version
        );
    }

    public Path getPath() {
        return Path.of(groupId.replace(".", "/"), artifactId, version);
    }

    public String getFilename(ArtefactFileType type) {
        return "%s-%s%s".formatted(artifactId, version, type.getExtension());
    }

    @Override
    public String toString() {
        return "%s:%s:%s".formatted(this.groupId, this.artifactId, this.version);
    }
}
