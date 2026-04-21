package ru.bolilyivs.dependency.manager.model.artefact;

public record ArtefactMetaData(
        String groupId,
        String artifactId,
        String version
) {
    public static ArtefactMetaData of(String artefactOneString) {
        String[] metaData = artefactOneString.split(":");
        return new ArtefactMetaData(
                metaData[0],
                metaData[1],
                metaData[2]
        );
    }

    public String getPath() {
        return "%s/%s/%s".formatted(groupId.replace(".", "/"), artifactId, version);
    }

    public String getFilename(ArtefactFileType type) {
        return "%s-%s%s".formatted(artifactId, version, type.getExtension());
    }

    @Override
    public String toString() {
        return "%s:%s:%s".formatted(this.groupId, this.artifactId, this.version);
    }
}
