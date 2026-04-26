package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.bolilyivs.server.data.model.RepoType;

@Serdeable
@Schema(name = "Repo", description = "Репозиторий")
public record RepoDto(
        @Schema(description = "Название", example = "central", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Название репозитория не должно быть пустым")
        String name,
        @Schema(description = "Адрес", example = "https://repo1.maven.org/maven2", requiredMode = Schema.RequiredMode.REQUIRED)
        String url,
        @Schema(description = "Тип репозитория", example = "REMOTE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Тип репозитория должно быть выбран: REMOTE, LOCAL")
        RepoType repoType
) {
}
