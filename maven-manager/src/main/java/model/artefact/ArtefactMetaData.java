package model.artefact;

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
}
