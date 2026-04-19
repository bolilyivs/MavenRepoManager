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

/**
 * Реализация интерфейса {@link DependencyResolver} с использованием Apache Ivy для разрешения зависимостей.
 */
public class IvyResolver implements DependencyResolver {

    private final String rootUrl; // URL корневого репозитория
    private final String name; // Имя resolver'а
    private ResolveEngine resolveEngine; // Экземпляр ResolveEngine
    private IvySettings ivySettings; // Настройки Ivy
    private URLResolver urlResolver; // Резолвер для работы с URL

    /**
     * Конструктор класса {@link IvyResolver}.
     *
     * @param rootUrl URL корневого репозитория
     * @param name    Имя resolver'а
     */
    public IvyResolver(String rootUrl, String name) {
        this.rootUrl = rootUrl;
        this.name = name;
        init();
    }

    /**
     * Инициализация resolver'а.
     */
    private void init() {
        urlResolver = createUrlResolver(); // Создание URL резолвера
        ivySettings = createIvySettings(urlResolver); // Создание настроек Ivy

        EventManager eventManager = new EventManager();
        SimpleSortEngineSettings ss = new SimpleSortEngineSettings();
        ss.setVersionMatcher(new MavenTimedSnapshotVersionMatcher());
        ss.setCircularDependencyStrategy(WarnCircularDependencyStrategy.getInstance());
        SortEngine sortEngine = new SortEngine(ss);

        resolveEngine = new ResolveEngine(ivySettings, eventManager, sortEngine);
    }

    /**
     * Создание URL резолвера.
     *
     * @return URL резолвер
     */
    private URLResolver createUrlResolver() {
        IBiblioResolver br = new IBiblioResolver();
        br.setRoot(rootUrl);
        br.setM2compatible(true);
        br.setUsepoms(true);
        br.setName(name);
        br.setUseMavenMetadata(true);
        return br;
    }

    /**
     * Создание настроек Ivy.
     *
     * @param urlResolver URL резолвер
     * @return Настройки Ivy
     */
    private IvySettings createIvySettings(URLResolver urlResolver) {
        IvySettings ivySettings = new IvySettings();
        ivySettings.setDefaultCache(new File("./ivy/%s/cache".formatted(name)));
        ivySettings.addResolver(urlResolver);
        ivySettings.setDefaultResolver(urlResolver.getName());
        return ivySettings;
    }

    /**
     * Резолв зависимостей.
     *
     * @param metaData Метаданные артефакта
     * @return Разрешенная зависимость
     */
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

    /**
     * Картировка Ivy зависимости.
     *
     * @param ivyNode Ivy узел
     * @return Ivy зависимость
     */
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

    /**
     * Картировка зависимостей.
     *
     * @param ivyDependencies Список Ivy зависимостей
     * @return Зависимость
     */
    private Dependency mapDependency(List<IvyDependency> ivyDependencies) {
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
    private Dependency mapDependency(IvyDependency node, Map<ArtefactMetaData, IvyDependency> mapDeps) {
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
