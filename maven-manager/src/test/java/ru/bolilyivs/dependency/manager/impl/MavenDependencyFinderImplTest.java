package ru.bolilyivs.dependency.manager.impl;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.ivy.impl.MavenArtefactMapperImpl;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.util.ArtefactIdTestFactory;
import ru.bolilyivs.dependency.manager.util.RepositoryTestFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

class MavenDependencyFinderImplTest {

    private static final String LOCAL_CACHE_DIR = "./cache/";


    @AfterEach
    void clear() throws IOException {
        File dir = Path.of(LOCAL_CACHE_DIR).toFile();
        if (dir.exists()) {
            FileUtils.deleteDirectory(Path.of(LOCAL_CACHE_DIR).toFile());
        }
    }

    @Test
    void resolve() {
        Repository repository = RepositoryTestFactory.createCentralTestRepository();
        MavenArtefactMapperImpl depMapper = new MavenArtefactMapperImpl();
        MavenDependencyFinder mavenDependencyFinder = new MavenDependencyFinderImpl(depMapper, LOCAL_CACHE_DIR);
        Artefact artefact = mavenDependencyFinder.resolve(repository, ArtefactIdTestFactory.createArtefactIdTest());

        Assertions.assertNotNull(artefact);
        Assertions.assertNotNull(artefact.getDependencies());
        Assertions.assertFalse(artefact.getDependencies().isEmpty());
    }
}