package ru.bolilyivs.dependency.manager.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.ivy.IvyConfigImpl;
import ru.bolilyivs.dependency.manager.ivy.IvyInstance;
import ru.bolilyivs.dependency.manager.ivy.IvyInstanceImpl;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.dependency.manager.model.dependency.IvyDependency;

import java.util.List;


@RequiredArgsConstructor
public class MavenDependencyFinderImpl implements MavenDependencyFinder {

    private final MavenArtefactMapper mavenArtefactMapper;
    private final String localCacheDir;

    @Override
    @SneakyThrows
    public Artefact resolve(Repository repository, ArtefactId metaData) {
        IvyConfig ivyConfig = IvyConfigImpl.of(repository, localCacheDir);
        IvyInstance ivyInstance = new IvyInstanceImpl(ivyConfig);
        ResolveReport resolveReport = ivyInstance.resolve(metaData);
        if (resolveReport.hasError()) {
            throw new RuntimeException(resolveReport.getAllProblemMessages().toString());
        }

        List<IvyDependency> ivyDependencyList = mavenArtefactMapper.mapIvyDependencyFrom(resolveReport);
        return mavenArtefactMapper.mapArtefactFrom(ivyDependencyList);
    }
}