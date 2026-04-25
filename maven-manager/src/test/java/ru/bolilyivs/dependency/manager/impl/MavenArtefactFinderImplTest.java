package ru.bolilyivs.dependency.manager.impl;

import org.junit.jupiter.api.Test;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

class MavenArtefactFinderImplTest {

    @Test
    void find() {
        Repository repository = new Repository(
                "central",
                "https://repo1.maven.org/maven2"
        );
        ArtefactMetaData artefactMetaData = ArtefactMetaData.of("org.springframework.boot:spring-boot-data-jpa:4.1.0-M1");

        MavenArtefactFinder finder = new MavenArtefactFinderImpl();
        Artefact artefact = finder.find(repository, artefactMetaData);
        IO.println(artefact);
    }
}