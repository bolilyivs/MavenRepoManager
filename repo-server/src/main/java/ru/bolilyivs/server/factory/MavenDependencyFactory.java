package ru.bolilyivs.server.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.MavenArtefactFinderImpl;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinderImpl;

@Factory
public class MavenDependencyFactory {

    @Singleton
    public MavenDependencyFinder mavenDependencyFinder() {
        return new MavenDependencyFinderImpl();
    }

    @Singleton
    public MavenArtefactFinder mavenArtefactFinder() {
        return new MavenArtefactFinderImpl();
    }
}
