package ru.bolilyivs.dependency.manager.impl;

import org.junit.jupiter.api.Test;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.ivy.impl.MavenDependencyMapperImpl;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;

class MavenDependencyFinderImplTest {

    @Test
    void resolve() {
        Repository repository = new Repository("maven", "https://repo1.maven.org/maven2");
        MavenDependencyMapperImpl depMapper = new MavenDependencyMapperImpl();
        MavenDependencyFinder mavenDependencyFinder = new MavenDependencyFinderImpl(depMapper, "./cache/");
        Dependency dependency = mavenDependencyFinder.resolve(repository, ArtefactMetaData.of("org.springframework.boot:spring-boot-data-jpa:4.1.0-M1"));
        IO.println(dependency.printTree(""));
    }
}