package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.bolilyivs.server.data.model.RepoType;

@Serdeable
@Schema(name = "RepoUpdateDto", description = "Репозиторий")
public record RepoUpdateDto(
        @Schema(description = "Адрес")
        String url,
        @Schema(description = "Тип репозитория")
        @NotNull(message = "Тип репозитория должно быть выбран: REMOTE, LOCAL")
        RepoType repoType
) {
}
