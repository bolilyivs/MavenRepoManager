package ru.bolilyivs.dependency.manager;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MavenArtefactFinderImpl implements MavenArtefactFinder {

    public static void main() {
        MavenArtefactFinder finder = new MavenArtefactFinderImpl();
        IO.println(finder.find("https://repo1.maven.org/maven2",
                ArtefactMetaData.of("org.springframework.boot:spring-boot-data-jpa:4.1.0-M1"))
        );
    }

    @Override
    @SneakyThrows
    public Artefact find(String repoBaseUrl, ArtefactMetaData metaData) {
        String groupPath = metaData.groupId().replace(".", "/");
        String url = "%s/%s/%s/%s/".formatted(repoBaseUrl, groupPath, metaData.artifactId(), metaData.version());

        Document doc = Jsoup.connect(url).get();
        Collection<ArtefactFile> files = doc.select("a")
                .stream()
                .map(e -> e.attr("href"))
                .filter(ArtefactFileType::isContainsExtension)
                .map(filename -> ArtefactFile.of(filename, ArtefactFileType.ofFileName(filename), metaData.getPath()))
                .collect(Collectors.toSet());
        Map<ArtefactFileType, ArtefactFile> mapFiles = files.stream()
                .collect(Collectors.toMap(ArtefactFile::type,
                        Function.identity(),
                        (existing, replacement) -> existing,
                        () -> new EnumMap<>(ArtefactFileType.class)
                ));
        return new Artefact(
                metaData,
                mapFiles
        );
    }
}
