package ru.bolilyivs.server.service;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bolilyivs.dependency.manager.MavenManager;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.data.model.RepoType;

import java.nio.file.Path;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class DownloadServiceImplTest {

    @Inject
    private DownloadService downloadService;

    @Inject
    private RepoService repoService;

    @Inject
    private MavenManager mavenManager;

    @MockBean(MavenManager.class)
    MavenManager mavenManager() {
        return mock(MavenManager.class);
    }

    @MockBean(RepoService.class)
    RepoService repoService() {
        return mock(RepoService.class);
    }

    @BeforeEach
    void setup() {
        Artefact excepted = createArtefact();

        when(mavenManager.resolveDependency(any(), any())).thenReturn(excepted);
        when(mavenManager.findArtefactWithFiles(any(), any())).thenReturn(excepted);
        when(repoService.get(any())).thenReturn(
                new RepoDto("test", "", RepoType.REMOTE)
        );
        when(mavenManager.downloadArtefactToFile(any(), any())).thenReturn(Path.of(""));
    }

    @Test
    void downloadArtifactWithDependencies() {
        Assertions.assertDoesNotThrow(() ->
                downloadService.downloadArtifactWithDependencies("test", "org.apache.commons:commons-text:1.15.0")
        );

    }

    @Test
    void downloadArtifact() {
        Assertions.assertDoesNotThrow(() ->
                downloadService.downloadArtifact("test", "org.apache.commons:commons-text:1.15.0")
        );
    }

    private Artefact createArtefact() {
        return Artefact.of(
                ArtefactId.of("org.apache.commons:commons-text:1.15.0"),
                List.of(
                        Artefact.of(
                                ArtefactId.of("org.apache.commons:commons-lang3:3.20.0"),
                                List.of(),
                                List.of(
                                        ArtefactFile.of(
                                                "commons-lang-3.20.0.jar",
                                                ArtefactFileType.JAR,
                                                ArtefactId.of("org.apache.commons:commons-lang3:3.20.0")
                                        )
                                )
                        )
                ),
                List.of(
                        ArtefactFile.of(
                                "commons-text-1.15.0.jar",
                                ArtefactFileType.JAR,
                                ArtefactId.of("org.apache.commons:commons-text:1.15.0")
                        )
                )
        );
    }
}