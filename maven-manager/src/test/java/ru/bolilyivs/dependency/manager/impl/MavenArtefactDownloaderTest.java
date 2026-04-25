package ru.bolilyivs.dependency.manager.impl;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import ru.bolilyivs.dependency.manager.MavenArtefactDownloader;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MavenArtefactDownloaderTest {

    private static String DOWNLOAD_PATH = "./download";
    private MavenArtefactDownloader downloader;

    @BeforeAll
    void setup() {
        this.downloader = new MavenArtefactDownloaderImpl(DOWNLOAD_PATH);
    }

    @AfterEach
    void clear() throws IOException {
        File dir = Path.of(DOWNLOAD_PATH).toFile();
        if (dir.exists()) {
            FileUtils.deleteDirectory(Path.of(DOWNLOAD_PATH).toFile());
        }
    }

    @Test
    void downloadArtefactToFile() {
        Repository repository = new Repository(
                "central",
                "https://repo1.maven.org/maven2"
        );
        ArtefactMetaData metaData = ArtefactMetaData.of("org.springframework.boot:spring-boot-data-jpa:4.1.0-M1");
        ArtefactFile artefactFile = ArtefactFile.of("spring-boot-data-jpa-4.1.0-M1.jar", ArtefactFileType.JAR, metaData);

        String path = this.downloader.downloadArtefactToFile(repository, artefactFile);
        Assertions.assertTrue(Files.exists(Path.of(path)));
    }

    @Test
    void downloadArtefact() throws IOException {
        Repository repository = new Repository(
                "central",
                "https://repo1.maven.org/maven2"
        );
        ArtefactMetaData metaData = ArtefactMetaData.of("org.springframework.boot:spring-boot-data-jpa:4.1.0-M1");
        ArtefactFile artefactFile = ArtefactFile.of("spring-boot-data-jpa-4.1.0-M1.jar", ArtefactFileType.JAR, metaData);

        Path targetPath = createTargetPath(artefactFile);

        try (InputStream is = this.downloader.downloadArtefact(repository, artefactFile)) {
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
        Assertions.assertTrue(Files.exists(targetPath));
    }

    @SneakyThrows
    private Path createTargetPath(ArtefactFile artefactFile) {
        Path targetPath = Path.of(DOWNLOAD_PATH, artefactFile.path().toString());
        Files.createDirectories(targetPath.getParent());
        return targetPath;
    }
}