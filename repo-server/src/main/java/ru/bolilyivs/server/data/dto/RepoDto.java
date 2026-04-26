package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.bolilyivs.server.data.model.RepoType;

@Serdeable
@Schema(name = "Repo", description = "Репозиторий")
public record RepoDto(
        @Schema(description = "Название", example = "central")
        String name,
        @Schema(description = "Адрес", example = "https://repo1.maven.org/maven2")
        String url,
        @Schema(description = "Тип репозитория", example = "REMOTE")
        RepoType repoType
) {
}
