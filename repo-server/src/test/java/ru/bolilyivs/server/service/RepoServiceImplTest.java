package ru.bolilyivs.server.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.data.dto.RepoUpdateDto;
import ru.bolilyivs.server.data.exception.RepoException;
import ru.bolilyivs.server.data.model.Repo;
import ru.bolilyivs.server.data.model.RepoType;
import ru.bolilyivs.server.data.repository.RepoRepository;

import java.util.List;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepoServiceImplTest {

    private final Repo ORIGIN = new Repo("test", "https://repo1.maven.org/maven2", RepoType.REMOTE);
    @Inject
    private RepoService repoService;
    @Inject
    private RepoRepository repository;
    ;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        repository.save(ORIGIN);
    }

    @Test
    void get() {
        RepoDto dto = repoService.get(ORIGIN.getName());
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ORIGIN.getName(), dto.name());
        Assertions.assertEquals(ORIGIN.getUrl(), dto.url());
    }

    @Test
    void list() {
        List<RepoDto> list = repoService.list();
        Assertions.assertEquals(1, list.size());

        Repo repo2 = new Repo("test2", "https://repo1.maven.org/maven2", RepoType.REMOTE);
        repository.save(repo2);

        list = repoService.list();
        Assertions.assertEquals(2, list.size());

        repository.deleteAll();

        list = repoService.list();
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void create() {
        RepoDto excepted = new RepoDto("test2", "https://repo1.maven.org/maven2", RepoType.REMOTE);
        repoService.create(excepted);
        RepoDto actual = repoService.get("test2");
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(excepted, actual);

    }

    @Test
    void update() {
        RepoUpdateDto excepted = new RepoUpdateDto("", RepoType.LOCAL);
        repoService.update(ORIGIN.getName(), excepted);

        RepoDto actual = repoService.get(ORIGIN.getName());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(excepted.url(), actual.url());
        Assertions.assertEquals(excepted.repoType(), actual.repoType());
    }

    @Test
    void delete() {
        repoService.delete(ORIGIN.getName());
        Assertions.assertThrows(RepoException.class, () ->
                repoService.get(ORIGIN.getName())
        );
    }
}