package ru.bolilyivs.dependency.manager.service;

import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.ivy.IvyConfigImpl;
import ru.bolilyivs.dependency.manager.ivy.IvyInstance;
import ru.bolilyivs.dependency.manager.ivy.IvyInstanceImpl;
import ru.bolilyivs.dependency.manager.model.Repository;

import java.util.List;

@RequiredArgsConstructor
public class MavenArtefactIdFinderImpl implements MavenArtefactIdFinder {

    private final String localCacheDir;

    @Override
    public List<String> listArtefactId(Repository repository, String groupId) {
        IvyConfig ivyConfig = IvyConfigImpl.of(repository, localCacheDir);
        IvyInstance ivyInstance = new IvyInstanceImpl(ivyConfig);
        return ivyInstance.listArtefactId(groupId);
    }

    @Override
    public List<String> listVersion(Repository repository, String groupId, String artefactId) {
        IvyConfig ivyConfig = IvyConfigImpl.of(repository, localCacheDir);
        IvyInstance ivyInstance = new IvyInstanceImpl(ivyConfig);
        return ivyInstance.listVersion(groupId, artefactId);
    }
}
