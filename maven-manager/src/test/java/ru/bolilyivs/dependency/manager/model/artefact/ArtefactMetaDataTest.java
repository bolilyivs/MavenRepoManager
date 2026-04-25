package ru.bolilyivs.dependency.manager.model.artefact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class ArtefactMetaDataTest {

    @Test
    void ofArtefactOneString() {
        String excepted = "org.springframework.boot:spring-boot-data-jpa:4.1.0-M1";
        ArtefactMetaData actual = ArtefactMetaData.of(excepted);
        Assertions.assertEquals(excepted, actual.toString());
        Assertions.assertEquals("org.springframework.boot", actual.groupId());
        Assertions.assertEquals("spring-boot-data-jpa", actual.artifactId());
        Assertions.assertEquals("4.1.0-M1", actual.version());
    }

    @Test
    void ofArtefactFile() {
        Path filePath = Path.of("org/springframework/boot/spring-boot-data-jpa/4.1.0-M1/spring-boot-data-jpa-4.1.0-M1.jar");
        ArtefactFile artefactFile = ArtefactFile.of(filePath.toString());
        ArtefactMetaData artefactMetaData = ArtefactMetaData.ofArtefactFile(artefactFile);
        Assertions.assertEquals(filePath.getParent(), artefactMetaData.getPath());
        Assertions.assertEquals("4.1.0-M1", artefactMetaData.version());
        Assertions.assertEquals("spring-boot-data-jpa", artefactMetaData.artifactId());
    }
}