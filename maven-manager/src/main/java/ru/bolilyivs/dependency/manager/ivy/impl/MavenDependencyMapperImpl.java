package ru.bolilyivs.dependency.manager.ivy.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ivy.core.module.descriptor.DependencyDescriptor;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import ru.bolilyivs.dependency.manager.ivy.MavenDependencyMapper;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.dependency.manager.model.dependency.IvyDependency;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MavenDependencyMapperImpl implements MavenDependencyMapper {

    @Override
    public List<IvyDependency> mapIvyDependency(ResolveReport resolveReport) {
        return resolveReport.getDependencies()
                .stream()
                .map(this::mapIvyDependency).toList();
    }

    /**
     * Картировка Ivy зависимости.
     *
     * @param ivyNode Ivy узел
     * @return Ivy зависимость
     */
    @Override
    public IvyDependency mapIvyDependency(IvyNode ivyNode) {
        ArtefactMetaData metaData = mapArtefactMetaData(ivyNode.getResolvedId());
        if (Objects.isNull(ivyNode.getDescriptor())) {
            return new IvyDependency(metaData, List.of());
        }
        List<ArtefactMetaData> deps = Stream.of(ivyNode.getDescriptor().getDependencies())
                .map(DependencyDescriptor::getDependencyRevisionId)
                .map(this::mapArtefactMetaData)
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
    public Dependency mapDependency(List<IvyDependency> ivyDependencies) {
        Map<ArtefactMetaData, IvyDependency> mapDeps = ivyDependencies.stream()
                .collect(Collectors.toMap(IvyDependency::metaData, Function.identity()));
        IvyDependency root = ivyDependencies.getFirst();
        return mapDependency(root, mapDeps);
    }

    /**
     * Рекурсивная картировка зависимости.
     *
     * @param node    Ivy зависимость
     * @param mapDeps Карта зависимостей по метаданным артефактов
     * @return Зависимость
     */
    @Override
    public Dependency mapDependency(IvyDependency node, Map<ArtefactMetaData, IvyDependency> mapDeps) {
        List<Dependency> deps = node.dependenies().stream()
                .map(iDepMetaData -> mapDeps.getOrDefault(iDepMetaData, null))
                .filter(Objects::nonNull)
                .map(ivyDependency -> mapDependency(ivyDependency, mapDeps))
                .toList();

        return new Dependency(node.metaData(), deps);
    }

    /**
     * Картировка метаданных артефакта.
     *
     * @param moduleRevisionId Идентификатор модуля
     * @return Метаданные артефакта
     */
    @Override
    public ArtefactMetaData mapArtefactMetaData(ModuleRevisionId moduleRevisionId) {
        return new ArtefactMetaData(
                moduleRevisionId.getOrganisation(),
                moduleRevisionId.getName(),
                moduleRevisionId.getRevision()
        );
    }
}
