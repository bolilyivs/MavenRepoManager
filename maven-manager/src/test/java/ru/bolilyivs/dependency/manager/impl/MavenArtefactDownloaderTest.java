package ru.bolilyivs.dependency.manager.impl;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.dependency.manager.service.MavenArtefactDownloader;
import ru.bolilyivs.dependency.manager.service.MavenArtefactDownloaderImpl;
import ru.bolilyivs.dependency.manager.util.ArtefactIdTestFactory;
import ru.bolilyivs.dependency.manager.util.RepositoryTestFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MavenArtefactDownloaderTest {

    public static final String FILENAME = "spring-boot-data-jpa-4.1.0-M1.jar";
    private static final String DOWNLOAD_PATH = "./download";
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
        Repository repository = RepositoryTestFactory.createCentralTestRepository();
        ArtefactId metaData = ArtefactIdTestFactory.createArtefactIdTest();
        ArtefactFile artefactFile = ArtefactFile.of(FILENAME, ArtefactFileType.JAR, metaData);

        Path actual = this.downloader.downloadArtefactToFile(repository, artefactFile);
        Assertions.assertTrue(Files.exists(actual));
    }

    @SneakyThrows
    private Path createTargetPath(ArtefactFile artefactFile) {
        Path targetPath = Path.of(DOWNLOAD_PATH, artefactFile.path().toString());
        Files.createDirectories(targetPath.getParent());
        return targetPath;
    }
}