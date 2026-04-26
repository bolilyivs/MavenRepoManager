package ru.bolilyivs.server;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.bolilyivs.server.service.RepoService;

@MicronautTest
class RepoServerApplicationTest {

    @Inject
    private ApplicationContext context;

    @Test
    void run() {
        RepoService repoService = Assertions.assertDoesNotThrow(() -> context.getBean(RepoService.class));
        Assertions.assertNotNull(repoService);
    }
}