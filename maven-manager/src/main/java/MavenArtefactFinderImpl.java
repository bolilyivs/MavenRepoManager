import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.artefact.Artefact;
import model.artefact.ArtefactFile;
import model.artefact.ArtefactFileType;
import model.artefact.ArtefactMetaData;
import model.dependency.Dependency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.http.HttpClient;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MavenArtefactFinderImpl implements MavenArtefactFinder {

    private final HttpClient client;


    public static void main() {
        HttpClient httpClient = HttpClient.newBuilder().build();
        MavenArtefactFinder finder = new MavenArtefactFinderImpl(httpClient);
        IO.println(finder.findArtefact("https://repo1.maven.org/maven2",
                ArtefactMetaData.of("org.springframework.boot:spring-boot-data-jpa:4.1.0-M1"))
        );
    }

    @Override
    @SneakyThrows
    public Artefact findArtefact(String repoBaseUrl, ArtefactMetaData metaData) {
        String groupPath = metaData.groupId().replace(".", "/");
        String url = "%s/%s/%s/%s/".formatted(repoBaseUrl, groupPath, metaData.artifactId(), metaData.version());

        Document doc = Jsoup.connect(url).get();
        Collection<ArtefactFile> files = doc.select("a")
                .stream()
                .map(e -> e.attr("href"))
                .filter(ArtefactFileType::isContainsExtension)
                .map(fileName -> new ArtefactFile(fileName, ArtefactFileType.ofFileName(fileName), getAbsoluteFilePath(url, fileName)))
                .collect(Collectors.toSet());
        return new Artefact(
                metaData,
                files
        );
    }

    private String getAbsoluteFilePath(String url, String fileName) {
        return "%s%s".formatted(url, fileName);
    }

    @Override
    public Dependency findDependency(String repoBaseUrl, ArtefactMetaData metaData) {
        return null;
    }
}
