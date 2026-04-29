package ru.bolilyivs.dependency.manager.service;

import lombok.RequiredArgsConstructor;
import org.apache.ivy.core.module.descriptor.DependencyDescriptor;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.dependency.manager.model.dependency.IvyDependency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MavenArtefactMapperImpl implements MavenArtefactMapper {

    @Override
    public List<IvyDependency> mapIvyDependencyFrom(ResolveReport resolveReport) {
        return resolveReport.getDependencies()
                .stream()
                .map(this::mapIvyDependencyFrom).toList();
    }

    /**
     * Картировка Ivy зависимости.
     *
     * @param ivyNode Ivy узел
     * @return Ivy зависимость
     */
    @Override
    public IvyDependency mapIvyDependencyFrom(IvyNode ivyNode) {
        ArtefactId metaData = mapArtefactIdFrom(ivyNode.getResolvedId());
        if (Objects.isNull(ivyNode.getDescriptor())) {
            return new IvyDependency(metaData, List.of());
        }
        List<ArtefactId> deps = Stream.of(ivyNode.getDescriptor().getDependencies())
                .map(DependencyDescriptor::getDependencyRevisionId)
                .map(this::mapArtefactIdFrom)
                .toList();
        return new IvyDependency(metaData, deps);
    }

    /**
     * Картировка зависимостей.
     *
     * @param ivyDependencies Список Ivy зависимостей
     * @return Зависимость
     */
    @Override
    public Artefact mapArtefactFrom(List<IvyDependency> ivyDependencies) {
        Map<ArtefactId, IvyDependency> mapDeps = ivyDependencies.stream()
                .collect(Collectors.toMap(IvyDependency::id, Function.identity()));
        IvyDependency root = ivyDependencies.getFirst();
        return mapArtefactFrom(root, mapDeps);
    }

    /**
     * Рекурсивная картировка зависимости.
     *
     * @param node    Ivy зависимость
     * @param mapDeps Карта зависимостей по метаданным артефактов
     * @return Зависимость
     */
    @Override
    public Artefact mapArtefactFrom(IvyDependency node, Map<ArtefactId, IvyDependency> mapDeps) {
        return mapArtefactFrom(node, mapDeps, new HashMap<>());
    }

    private Artefact mapArtefactFrom(IvyDependency node, Map<ArtefactId, IvyDependency> mapDeps, Map<ArtefactId, Artefact> cache) {
        Artefact artefact = cache.computeIfAbsent(node.id(), Artefact::of);

        List<Artefact> deps = node.dependencies().stream()
                .map(iDepMetaData -> mapDeps.getOrDefault(iDepMetaData, null))
                .filter(Objects::nonNull)
                .map(ivyDependency -> mapArtefactFrom(ivyDependency, mapDeps))
                .toList();
        artefact.setDependencies(deps);
        return artefact;
    }

    /**
     * Картировка метаданных артефакта.
     *
     * @param moduleRevisionId Идентификатор модуля
     * @return Метаданные артефакта
     */
    @Override
    public ArtefactId mapArtefactIdFrom(ModuleRevisionId moduleRevisionId) {
        return new ArtefactId(
                moduleRevisionId.getOrganisation(),
                moduleRevisionId.getName(),
                moduleRevisionId.getRevision()
        );
    }
}
