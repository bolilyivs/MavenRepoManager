package ru.bolilyivs.dependency.manager.model.artefact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class ArtefactIdTest {

    @Test
    void ofArtefactOneString() {
        String excepted = "org.springframework.boot:spring-boot-data-jpa:4.1.0-M1";
        ArtefactId actual = ArtefactId.of(excepted);
        Assertions.assertEquals(excepted, actual.toString());
        Assertions.assertEquals("org.springframework.boot", actual.groupId());
        Assertions.assertEquals("spring-boot-data-jpa", actual.artifactId());
        Assertions.assertEquals("4.1.0-M1", actual.version());
    }

    @Test
    void ofArtefactFile() {
        Path filePath = Path.of("org/springframework/boot/spring-boot-data-jpa/4.1.0-M1/spring-boot-data-jpa-4.1.0-M1.jar");
        ArtefactFile artefactFile = ArtefactFile.of(filePath.toString());
        ArtefactId artefactId = ArtefactId.ofArtefactFile(artefactFile);
        Assertions.assertEquals(filePath.getParent(), artefactId.getPath());
        Assertions.assertEquals("4.1.0-M1", artefactId.version());
        Assertions.assertEquals("spring-boot-data-jpa", artefactId.artifactId());
    }
}