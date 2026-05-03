package ru.bolilyivs.dependency.manager.ivy;

import lombok.SneakyThrows;
import org.apache.ivy.Ivy;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.Arrays;
import java.util.List;

public class IvyInstanceImpl implements IvyInstance {
    private final IvyConfig config;
    private final Ivy ivy;

    public IvyInstanceImpl(IvyConfig config) {
        this.config = config;
        this.ivy = Ivy.newInstance(config.getSettings());
    }

    @SneakyThrows
    @Override
    public ResolveReport resolve(ArtefactId metaData) {
        ModuleRevisionId ri = ModuleRevisionId.newInstance(
                metaData.groupId(),
                metaData.artifactId(),
                metaData.version()
        );
        return ivy.resolve(ri, this.config.getResolveOptions(), true);
    }

    @Override
    public List<String> listArtefactId(String groupId) {
        return Arrays.asList(ivy.listModules(groupId));
    }

    @Override
    public List<String> listVersion(String groupId, String artefactId) {
        return Arrays.asList(ivy.listRevisions(groupId, artefactId));
    }
}
