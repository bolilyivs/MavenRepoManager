package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.bolilyivs.server.data.model.RepoType;

@Serdeable
@Schema(name = "Repo", description = "Репозиторий")
public record RepoDto(
        @Schema(description = "Название")
        String name,
        @Schema(description = "Адрес")
        String url,
        @Schema(description = "Тип репозитория")
        RepoType repoType
) {
}
