package ru.bolilyivs.dependency.manager.resolver;

import lombok.SneakyThrows;
import org.apache.ivy.core.event.EventManager;
import org.apache.ivy.core.module.descriptor.DependencyDescriptor;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import org.apache.ivy.core.resolve.ResolveEngine;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.core.sort.SimpleSortEngineSettings;
import org.apache.ivy.core.sort.SortEngine;
import org.apache.ivy.plugins.circular.WarnCircularDependencyStrategy;
import org.apache.ivy.plugins.resolver.IBiblioResolver;
import org.apache.ivy.plugins.resolver.URLResolver;
import org.apache.ivy.plugins.version.MavenTimedSnapshotVersionMatcher;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IvyResolver implements DependencyResolver {

    private final String rootUrl;
    private final String name;
    private ResolveEngine resolveEngine;
    private IvySettings ivySettings;
    private URLResolver urlResolver;

    public IvyResolver(String rootUrl, String name) {
        this.rootUrl = rootUrl;
        this.name = name;
        init();
    }

    private void init() {
        urlResolver = createUrlResolver();
        ivySettings = createIvySettings(urlResolver);

        EventManager eventManager = new EventManager();
        SimpleSortEngineSettings ss = new SimpleSortEngineSettings();
        ss.setVersionMatcher(new MavenTimedSnapshotVersionMatcher());
        ss.setCircularDependencyStrategy(WarnCircularDependencyStrategy.getInstance());
        SortEngine sortEngine = new SortEngine(ss);

        resolveEngine = new ResolveEngine(ivySettings, eventManager, sortEngine);
    }

    private URLResolver createUrlResolver() {
        IBiblioResolver br = new IBiblioResolver();
        br.setRoot(rootUrl);
        br.setM2compatible(true);
        br.setUsepoms(true);
        br.setName(name);
        br.setUseMavenMetadata(true);
        return br;
    }

    private IvySettings createIvySettings(URLResolver urlResolver) {
        IvySettings ivySettings = new IvySettings();
        ivySettings.setDefaultCache(new File("./ivy/%s/cache".formatted(name)));
        ivySettings.addResolver(urlResolver);
        ivySettings.setDefaultResolver(urlResolver.getName());
        return ivySettings;
    }

    @SneakyThrows
    public Dependency resolver(ArtefactMetaData metaData) {
        ModuleRevisionId ri = ModuleRevisionId.newInstance(
                metaData.groupId(),
                metaData.artifactId(),
                metaData.version()
        );

        ResolveOptions ro = new ResolveOptions();
        ro.setTransitive(true);
        ro.setDownload(false);
        ro.setOutputReport(false);

        ResolveReport resolveReport = resolveEngine.resolve(ri, ro, true);
        List<IvyDependency> ivyDeps = resolveReport.getDependencies()
                .stream()
                .map(this::mapIvyDependency).toList();
        return mapDependency(ivyDeps);
    }

    private IvyDependency mapIvyDependency(IvyNode ivyNode) {
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

    private Dependency mapDependency(List<IvyDependency> ivyDependencies) {
        Map<ArtefactMetaData, IvyDependency> mapDeps = ivyDependencies.stream()
                .collect(Collectors.toMap(IvyDependency::metaData, Function.identity()));
        IvyDependency root = ivyDependencies.getFirst();
        return mapDependency(root, mapDeps);
    }

    private Dependency mapDependency(IvyDependency node, Map<ArtefactMetaData, IvyDependency> mapDeps) {
        List<Dependency> deps = node.dependenies().stream()
                .map(iDepMetaData -> mapDeps.getOrDefault(iDepMetaData, null))
                .filter(Objects::nonNull)
                .map(ivyDependency -> mapDependency(ivyDependency, mapDeps))
                .toList();

        return new Dependency(node.metaData(), deps);
    }

    private ArtefactMetaData mapArtefactMetaData(ModuleRevisionId moduleRevisionId) {
        return new ArtefactMetaData(
                moduleRevisionId.getOrganisation(),
                moduleRevisionId.getName(),
                moduleRevisionId.getRevision()
        );
    }

    record IvyDependency(ArtefactMetaData metaData, List<ArtefactMetaData> dependenies) {
    }


}
