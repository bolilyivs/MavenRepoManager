package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

@Serdeable
@Schema(name = "ArtedactRequestDTO", description = "Запрос на артефакт", example = """
        {
            "moduleDependency": "org.apache.commons:commons-text:1.15.0"
        }
        """)
public record ArtefactRequestDTO(
        @Schema(description = "Организация", example = "org.apache.commons:commons-text:1.15.0")
        String moduleDependency
) {

}
