package ru.bolilyivs.dependency.manager.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MavenArtefactDownloaderImpl implements MavenArtefactDownloader {

    private final String localRootRepoDir;

    @Override
    @SneakyThrows
    public Path downloadArtefactToFile(Repository repository, ArtefactFile artefactFile) {
        Path targetPath = createTargetPath(repository, artefactFile);

        if (Files.exists(targetPath)) {
            return targetPath.toAbsolutePath();
        }
        downloadArtefact(repository, artefactFile, is -> copy(is, targetPath));
        return targetPath.toAbsolutePath();
    }


    @Override
    @SneakyThrows
    public void downloadArtefact(Repository repository, ArtefactFile artefactFile, Consumer<InputStream> process) {
        URI srcURI = createURI(repository, artefactFile);
        HttpRequest request = createHttpRequest(srcURI);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<InputStream> response = client.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());
            try (InputStream is = response.body()) {
                process.accept(is);
            }
        }
    }

    @SneakyThrows
    private void copy(InputStream is, Path targetPath) {
        Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    private HttpRequest createHttpRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
    }

    private URI createURI(Repository repository, ArtefactFile artefactFile) {
        return URI.create("%s/%s".formatted(repository.url(), artefactFile.path().toString().replace("\\", "/")));
    }

    @SneakyThrows
    private Path createTargetPath(Repository repository, ArtefactFile artefactFile) {
        Path targetPath = Path.of(localRootRepoDir, repository.name(), artefactFile.path().toString().replace("\\", "/"));
        Files.createDirectories(targetPath.getParent());
        return targetPath;
    }
}
