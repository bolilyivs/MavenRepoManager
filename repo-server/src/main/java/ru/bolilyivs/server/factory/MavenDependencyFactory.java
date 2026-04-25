package ru.bolilyivs.server.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenArtefactDownloader;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.impl.MavenArtefactDownloaderImpl;
import ru.bolilyivs.dependency.manager.impl.MavenArtefactFinderImpl;
import ru.bolilyivs.dependency.manager.impl.MavenDependencyFinderImpl;
import ru.bolilyivs.dependency.manager.ivy.impl.MavenDependencyMapperImpl;
import ru.bolilyivs.server.config.AppConfig;

@Factory
@RequiredArgsConstructor
public class MavenDependencyFactory {

    private final AppConfig appConfig;

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

    @Singleton
    public MavenArtefactDownloader mavenArtefactDownloader() {
        return new MavenArtefactDownloaderImpl(appConfig.getRootRepoDir());
    }
}
