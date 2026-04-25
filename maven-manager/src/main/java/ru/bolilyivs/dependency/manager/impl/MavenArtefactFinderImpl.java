package ru.bolilyivs.dependency.manager.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.bolilyivs.dependency.manager.MavenArtefactFinder;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MavenArtefactFinderImpl implements MavenArtefactFinder {

    @Override
    @SneakyThrows
    public Artefact find(Repository repository, ArtefactMetaData metaData) {
        String groupPath = metaData.groupId().replace(".", "/");
        String url = "%s/%s/%s/%s/".formatted(repository.url(), groupPath, metaData.artifactId(), metaData.version());

        Document doc = Jsoup.connect(url).get();
        List<ArtefactFile> files = doc.select("a")
                .stream()
                .map(e -> e.attr("href"))
                .filter(ArtefactFileType::isContainsExtension)
                .map(filename -> ArtefactFile.of(filename, ArtefactFileType.ofFileName(filename), metaData.getPath().toString()))
                .collect(Collectors.toList());

        return new Artefact(
                metaData,
                files
        );
    }
}
