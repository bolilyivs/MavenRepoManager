package ru.bolilyivs.dependency.manager.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.MavenDependencyFinder;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;
import ru.bolilyivs.dependency.manager.ivy.IvyInstance;
import ru.bolilyivs.dependency.manager.ivy.MavenDependencyMapper;
import ru.bolilyivs.dependency.manager.ivy.impl.IvyConfigImpl;
import ru.bolilyivs.dependency.manager.ivy.impl.IvyInstanceImpl;
import ru.bolilyivs.dependency.manager.ivy.impl.MavenDependencyMapperImpl;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.dependency.manager.model.dependency.IvyDependency;

import java.util.List;


@RequiredArgsConstructor
public class MavenDependencyFinderImpl implements MavenDependencyFinder {

    private final MavenDependencyMapper mavenDependencyMapper;

    public static void main() {
        IvyConfig conf = new IvyConfigImpl(
                "maven",
                "https://repo1.maven.org/maven2",
                "/home/shenlong/repos/cache/"
        );
        MavenDependencyMapperImpl depMapper = new MavenDependencyMapperImpl();
        MavenDependencyFinder mavenDependencyFinder = new MavenDependencyFinderImpl(depMapper);
        Dependency dependency = mavenDependencyFinder.find(conf, ArtefactMetaData.of("org.springframework.boot:spring-boot-data-jpa:4.1.0-M1"));
        IO.println(dependency.printTree(""));
        System.exit(0);
    }

    @Override
    @SneakyThrows
    public Dependency find(IvyConfig ivyConfig, ArtefactMetaData metaData) {
        IvyInstance ivyInstance = new IvyInstanceImpl(ivyConfig);
        ResolveReport resolveReport = ivyInstance.resolve(metaData);
        List<IvyDependency> ivyDependencyList = mavenDependencyMapper.mapIvyDependency(resolveReport);
        return mavenDependencyMapper.mapDependency(ivyDependencyList);
    }
}