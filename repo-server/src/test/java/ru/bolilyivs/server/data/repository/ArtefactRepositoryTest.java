package ru.bolilyivs.server.data.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.server.data.model.ArtefactEntity;
import ru.bolilyivs.server.data.model.ArtefactFileEntity;

import java.util.Set;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtefactRepositoryTest {

    @Inject
    private ArtefactRepository artefactRepository;

    @Test
    void crud() {
        ArtefactEntity root = new ArtefactEntity();
        root.setArtefactId("org.apache.commons:commons-text:1.15.0");

        ArtefactFileEntity artefactFileEntity = new ArtefactFileEntity();
        artefactFileEntity.setFilename("commons-text-1.15.0.jar");
        artefactFileEntity.setType(ArtefactFileType.JAR);
        artefactFileEntity.setPath("path");
        root.addFile(artefactFileEntity);

        ArtefactEntity dep = new ArtefactEntity();
        dep.setArtefactId("org.apache.commons:commons-lang3:3.20.0");
        root.setDependency(Set.of(dep));

        artefactFileEntity = new ArtefactFileEntity();
        artefactFileEntity.setFilename("commons-lang3-3.20.0.jar");
        artefactFileEntity.setType(ArtefactFileType.JAR);
        artefactFileEntity.setPath("path");
        root.addFile(artefactFileEntity);

        artefactRepository.save(root);

        ArtefactEntity actual = artefactRepository.findById("org.apache.commons:commons-text:1.15.0")
                .orElse(null);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getDependency());
        Assertions.assertFalse(actual.getDependency().isEmpty());
    }

}