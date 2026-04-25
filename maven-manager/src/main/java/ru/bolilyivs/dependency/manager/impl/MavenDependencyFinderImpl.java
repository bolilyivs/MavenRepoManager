package ru.bolilyivs.dependency.manager.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.ivy.IvyInstance;
import ru.bolilyivs.dependency.manager.ivy.MavenDependencyMapper;
import ru.bolilyivs.dependency.manager.ivy.impl.IvyConfigImpl;
import ru.bolilyivs.dependency.manager.ivy.impl.IvyInstanceImpl;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.dependency.manager.model.dependency.IvyDependency;

import java.util.List;


@RequiredArgsConstructor
public class MavenDependencyFinderImpl implements MavenDependencyFinder {

    private final MavenDependencyMapper mavenDependencyMapper;
    private final String localCacheDir;

    @Override
    @SneakyThrows
    public Dependency resolve(Repository repository, ArtefactMetaData metaData) {
        IvyConfig ivyConfig = new IvyConfigImpl(
                repository.name(),
                repository.name(),
                localCacheDir
        );
        IvyInstance ivyInstance = new IvyInstanceImpl(ivyConfig);
        ResolveReport resolveReport = ivyInstance.resolve(metaData);
        List<IvyDependency> ivyDependencyList = mavenDependencyMapper.mapIvyDependency(resolveReport);
        return mavenDependencyMapper.mapDependency(ivyDependencyList);
    }
}