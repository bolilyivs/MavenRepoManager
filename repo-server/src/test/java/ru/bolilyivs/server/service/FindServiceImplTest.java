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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class FindServiceImplTest {

    @Inject
    private FindService findService;

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
        when(repoService.get(any())).thenReturn(
                new RepoDto("test", "", RepoType.REMOTE)
        );
    }

    @Test
    void findArtefactWithFilesWithDependencies() {
        Artefact excepted = Artefact.ofDependencies(
                ArtefactId.of("org.apache.commons:commons-text:1.15.0"),
                List.of(
                        Artefact.of(ArtefactId.of("org.apache.commons:commons-lang3:3.20.0"))
                )
        );
        when(mavenManager.resolveDependency(any(), any())).thenReturn(excepted);

        Artefact actual = findService.findArtefactWithDependencies("test", "org.apache.commons:commons-text:1.15.0");

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(excepted.getId(), actual.getId());
        Assertions.assertFalse(actual.getDependencies().isEmpty());

    }

    @Test
    void findArtefactWithFiles() {
        Artefact excepted = Artefact.ofArtefactFiles(
                ArtefactId.of("org.apache.commons:commons-text:1.15.0"),
                List.of(
                        ArtefactFile.of(
                                "commons-text-1.15.0.jar",
                                ArtefactFileType.JAR,
                                ArtefactId.of("org.apache.commons:commons-text:1.15.0")
                        )
                )
        );
        when(mavenManager.findArtefactWithFiles(any(), any())).thenReturn(excepted);

        Artefact actual = findService.findArtefactWithFiles("test", "org.apache.commons:commons-text:1.15.0");

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(excepted.getId(), actual.getId());
        Assertions.assertFalse(actual.getFiles().isEmpty());
        Assertions.assertTrue(actual.getFiles().contains(excepted.getFiles().getFirst()));
    }
}