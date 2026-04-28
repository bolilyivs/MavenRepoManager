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

        try (InputStream is = downloadArtefact(repository, artefactFile)) {
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
        return targetPath.toAbsolutePath();
    }


    @Override
    public InputStream downloadArtefact(Repository repository, ArtefactFile artefactFile) {
        URI srcURI = createURI(repository, artefactFile);
        HttpRequest request = createHttpRequest(srcURI);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<InputStream> response = client.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());
            return response.body();
        }

        return InputStream.nullInputStream();
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
