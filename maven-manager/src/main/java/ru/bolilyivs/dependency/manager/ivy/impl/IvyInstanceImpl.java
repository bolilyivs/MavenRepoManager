package ru.bolilyivs.dependency.manager.ivy.impl;

import lombok.SneakyThrows;
import org.apache.ivy.Ivy;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.ivy.IvyInstance;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

public class IvyInstanceImpl implements IvyInstance {
    private final IvyConfig config;
    private final Ivy ivy;

    public IvyInstanceImpl(IvyConfig config) {
        this.config = config;
        this.ivy = Ivy.newInstance(config.getSettings());
    }

    @SneakyThrows
    @Override
    public ResolveReport resolve(ArtefactMetaData metaData) {
        ModuleRevisionId ri = ModuleRevisionId.newInstance(
                metaData.groupId(),
                metaData.artifactId(),
                metaData.version()
        );
        return ivy.resolve(ri, this.config.getResolveOptions(), true);
    }
}
