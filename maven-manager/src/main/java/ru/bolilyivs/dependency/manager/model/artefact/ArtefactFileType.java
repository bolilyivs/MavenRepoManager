package ru.bolilyivs.dependency.manager.model.artefact;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public enum ArtefactFileType {
    JAVADOC("-javadoc.jar"),
    SOURCES_JAR("-sources.jar"),
    JAR(".jar"),
    MODULE(".module"),
    POM(".pom");

    @Getter
    private final String extension;

    public static Collection<ArtefactFileType> getList() {
        return List.of(ArtefactFileType.values());
    }

    public static ArtefactFileType ofFileName(String filename) {
        return getList().stream()
                .filter(fileType -> filename.endsWith(fileType.getExtension()))
                .findFirst().orElseThrow();
    }

    public static boolean isContainsExtension(String fileName) {
        return getList().stream()
                .map(ArtefactFileType::getExtension)
                .anyMatch(fileName::endsWith);
    }

}
