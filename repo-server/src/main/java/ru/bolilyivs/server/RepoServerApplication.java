package ru.bolilyivs.server;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Repo Server",
                version = "0.1",
                description = "Менеджер репозиториев для артефактов Maven"
        )
)
public class RepoServerApplication {
    static void main(String[] args) {
        Micronaut.run(RepoServerApplication.class);
    }
}
