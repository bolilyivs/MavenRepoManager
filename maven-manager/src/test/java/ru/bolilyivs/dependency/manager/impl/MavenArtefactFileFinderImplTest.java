package ru.bolilyivs.dependency.manager.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.bolilyivs.dependency.manager.MavenArtefactFileFinder;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.dependency.manager.util.ArtefactIdTestFactory;
import ru.bolilyivs.dependency.manager.util.RepositoryTestFactory;

import java.util.List;

class MavenArtefactFileFinderImplTest {

    @Test
    void find() {
        Repository repository = RepositoryTestFactory.createCentralTestRepository();
        ArtefactId artefactId = ArtefactIdTestFactory.createArtefactIdTest();

        MavenArtefactFileFinder finder = new MavenArtefactFileFinderImpl();
        List<ArtefactFile> artefactFiles = finder.find(repository, artefactId);
        Assertions.assertNotNull(artefactFiles);
        Assertions.assertFalse(artefactFiles.isEmpty());
    }
}