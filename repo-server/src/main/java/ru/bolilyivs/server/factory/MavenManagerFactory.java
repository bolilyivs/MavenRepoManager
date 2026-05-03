package ru.bolilyivs.server.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenManager;
import ru.bolilyivs.dependency.manager.MavenManagerImpl;
import ru.bolilyivs.dependency.manager.service.*;
import ru.bolilyivs.server.config.AppConfig;

@Factory
@RequiredArgsConstructor
public class MavenManagerFactory {

    private final AppConfig appConfig;

    @Singleton
    public MavenArtefactIdFinder mavenArtefactIdFinder() {
        return new MavenArtefactIdFinderImpl(appConfig.getCacheDir());
    }

    @Singleton
    public MavenDependencyFinder mavenDependencyFinder() {
        MavenArtefactMapper mavenArtefactMapper = new MavenArtefactMapperImpl();
        return new MavenDependencyFinderImpl(mavenArtefactMapper, appConfig.getCacheDir());
    }

    @Singleton
    public MavenArtefactFileFinder mavenArtefactFinder() {
        return new MavenArtefactFileFinderImpl();
    }

    @Singleton
    public MavenArtefactDownloader mavenArtefactDownloader() {
        return new MavenArtefactDownloaderImpl(appConfig.getRootRepoDir());
    }

    @Singleton
    public MavenManager mavenManager(
            MavenArtefactIdFinder mavenArtefactIdFinder,
            MavenDependencyFinder mavenDependencyFinder,
            MavenArtefactFileFinder mavenArtefactFinder,
            MavenArtefactDownloader mavenArtefactDownloader
    ) {
        return new MavenManagerImpl(
                mavenArtefactIdFinder,
                mavenDependencyFinder,
                mavenArtefactFinder,
                mavenArtefactDownloader
        );
    }
}
