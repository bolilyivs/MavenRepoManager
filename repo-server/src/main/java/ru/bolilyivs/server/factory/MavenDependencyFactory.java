package ru.bolilyivs.server.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.MavenArtefactDownloader;
import ru.bolilyivs.dependency.manager.MavenArtefactFileFinder;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.impl.MavenArtefactDownloaderImpl;
import ru.bolilyivs.dependency.manager.impl.MavenArtefactFileFinderImpl;
import ru.bolilyivs.dependency.manager.impl.MavenDependencyFinderImpl;
import ru.bolilyivs.dependency.manager.ivy.MavenArtefactMapper;
import ru.bolilyivs.dependency.manager.ivy.impl.MavenArtefactMapperImpl;
import ru.bolilyivs.server.config.AppConfig;

@Factory
@RequiredArgsConstructor
public class MavenDependencyFactory {

    private final AppConfig appConfig;

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
}
