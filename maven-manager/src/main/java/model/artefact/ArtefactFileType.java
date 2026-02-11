package model.artefact;

import org.apache.commons.io.FilenameUtils;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public enum ArtefactFileType {
    JAVADOC,
    SOURCES,
    JAR,
    MODULE,
    POM;

    public static Collection<ArtefactFileType> getList() {
        return Set.of(ArtefactFileType.values());
    }

    public static Collection<String> getNameList() {
        return getList().stream().map(ArtefactFileType::name).collect(Collectors.toSet());
    }

    public static ArtefactFileType ofFileName(String fileName) {
        return ArtefactFileType.valueOf(FilenameUtils.getExtension(fileName).toUpperCase());
    }

    public static boolean isContainsExtension(String fileName) {
        return getNameList().contains(FilenameUtils.getExtension(fileName).toUpperCase());
    }

}
