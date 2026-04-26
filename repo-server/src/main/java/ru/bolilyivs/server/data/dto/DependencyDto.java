package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.List;

@Serdeable
@Schema(name = "DependencyDto", description = "Зависимость")
public record DependencyDto(
        @Schema
        String groupId,
        @Schema
        String artifactId,
        @Schema
        String version,
        @Schema
        List<DependencyDto> dependencies
) {
    public static DependencyDto ofArtefact(Artefact artefact) {
        ArtefactId metaData = artefact.getId();
        return new DependencyDto(
                metaData.groupId(),
                metaData.artifactId(),
                metaData.version(),
                artefact.getDependencies()
                        .stream()
                        .map(DependencyDto::ofArtefact)
                        .toList()
        );
    }
}
