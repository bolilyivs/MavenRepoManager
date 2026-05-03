package ru.bolilyivs.dependency.manager.model.artefact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class ArtefactFileTest {

    @Test
    void ofFilePath() {
        Path filePath = Path.of("org/springframework/boot/spring-boot-data-jpa/4.1.0-M1/spring-boot-data-jpa-4.1.0-M1.jar");
        ArtefactFile artefactFile = ArtefactFile.of(filePath.toString());
        Assertions.assertEquals("spring-boot-data-jpa-4.1.0-M1.jar", artefactFile.filename());
        Assertions.assertEquals(ArtefactFileType.JAR, artefactFile.type());
        Assertions.assertEquals(filePath, artefactFile.path());
    }
}