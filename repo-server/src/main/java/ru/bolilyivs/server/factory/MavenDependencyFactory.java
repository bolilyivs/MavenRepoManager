package ru.bolilyivs.server.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.impl.MavenArtefactFinderImpl;
import ru.bolilyivs.dependency.manager.impl.MavenDependencyFinderImpl;
import ru.bolilyivs.dependency.manager.ivy.impl.MavenDependencyMapperImpl;

@Factory
public class MavenDependencyFactory {

    @Singleton
    public MavenDependencyMapperImpl mavenDependencyMapper() {
        return new MavenDependencyMapperImpl();
    }

    @Singleton
    public MavenDependencyFinder mavenDependencyFinder() {
        return new MavenDependencyFinderImpl(mavenDependencyMapper());
    }

    @Singleton
    public MavenArtefactFinder mavenArtefactFinder() {
        return new MavenArtefactFinderImpl();
    }
}
