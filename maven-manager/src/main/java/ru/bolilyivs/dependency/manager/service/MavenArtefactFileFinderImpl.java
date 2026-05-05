package ru.bolilyivs.dependency.manager.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.dependency.manager.model.exception.MavenManagerException;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class MavenArtefactFileFinderImpl implements MavenArtefactFileFinder {

    @Override
    public List<ArtefactFile> find(Repository repository, ArtefactId metaData) {
        String groupPath = metaData.groupId().replace(".", "/");
        String url = "%s/%s/%s/%s/".formatted(repository.url(), groupPath, metaData.artifactId(), metaData.version());

        try {
            Document doc = Jsoup.connect(url).get();
            return doc.select("a")
                    .stream()
                    .map(e -> e.attr("href"))
                    .filter(ArtefactFileType::isContainsExtension)
                    .map(filename -> ArtefactFile.of(filename, ArtefactFileType.ofFileName(filename), metaData.getPath().toString()))
                    .toList();
        } catch (IOException ioException) {
            throw new MavenManagerException("Ошибка при получении страницы артефакта с файлами", ioException);
        }
    }
}
