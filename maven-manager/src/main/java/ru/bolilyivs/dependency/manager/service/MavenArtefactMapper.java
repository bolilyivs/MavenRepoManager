package ru.bolilyivs.dependency.manager.service;

import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.dependency.manager.model.dependency.IvyDependency;

import java.util.List;
import java.util.Map;

public interface MavenArtefactMapper {
    List<IvyDependency> mapIvyDependencyFrom(ResolveReport resolveReport);

    IvyDependency mapIvyDependencyFrom(IvyNode ivyNode);

    Artefact mapArtefactFrom(List<IvyDependency> ivyDependencies);

    Artefact mapArtefactFrom(IvyDependency node, Map<ArtefactId, IvyDependency> mapDeps);

    ArtefactId mapArtefactIdFrom(ModuleRevisionId moduleRevisionId);
}
