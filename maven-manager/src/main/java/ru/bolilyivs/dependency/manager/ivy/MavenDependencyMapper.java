package ru.bolilyivs.dependency.manager.ivy;

import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.dependency.manager.model.dependency.IvyDependency;

import java.util.List;
import java.util.Map;

public interface MavenDependencyMapper {
    List<IvyDependency> mapIvyDependency(ResolveReport resolveReport);

    IvyDependency mapIvyDependency(IvyNode ivyNode);

    Dependency mapDependency(List<IvyDependency> ivyDependencies);

    Dependency mapDependency(IvyDependency node, Map<ArtefactMetaData, IvyDependency> mapDeps);

    ArtefactMetaData mapArtefactMetaData(ModuleRevisionId moduleRevisionId);
}
