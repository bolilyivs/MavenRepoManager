package ru.bolilyivs.dependency.manager;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.dependency.manager.model.dependency.Dependency;
import ru.bolilyivs.dependency.manager.resolver.IvyResolver;


@RequiredArgsConstructor
public class MavenDependencyFinderImpl implements MavenDependencyFinder {

    public static void main() {
        MavenDependencyFinder finder = new MavenDependencyFinderImpl();
        Dependency dep = finder.find("https://repo1.maven.org/maven2", "central",
                ArtefactMetaData.of("org.springframework.boot:spring-boot-starter-data-jpa:4.1.0-M1"));
        IO.println(dep.printTree(""));
        System.exit(0);
    }

    @Override
    @SneakyThrows
    public Dependency find(String repoBaseUrl, String name, ArtefactMetaData metaData) {
        IvyResolver ivyResolver = new IvyResolver(repoBaseUrl, name);
        return ivyResolver.resolver(metaData);
    }
}