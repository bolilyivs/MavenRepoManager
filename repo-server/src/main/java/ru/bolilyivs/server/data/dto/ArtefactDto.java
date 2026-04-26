package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.List;

@Serdeable
@Schema(name = "DependencyDto", description = "Зависимость")
public record ArtefactDto(
        @Schema
        String groupId,
        @Schema
        String artifactId,
        @Schema
        String version,
        @Schema
        List<ArtefactFileDto> files,
        @Schema
        List<ArtefactDto> dependencies
) {

    public static ArtefactDto of(Artefact artefact) {
        ArtefactId artefactId = artefact.getId();
        List<ArtefactFileDto> files = artefact.getFiles().stream().map(ArtefactFileDto::of).toList();
        List<ArtefactDto> dependencies = artefact.getDependencies().stream().map(ArtefactDto::of).toList();
        return new ArtefactDto(artefactId.groupId(), artefactId.artifactId(), artefactId.version(), files, dependencies);
    }

    @Serdeable
    public record ArtefactFileDto(
            @Schema
            String filename,
            @Schema
            String type,
            @Schema
            String path
    ) {

        public static ArtefactFileDto of(ArtefactFile artefactFile) {
            return new ArtefactFileDto(artefactFile.filename(), artefactFile.type().toString(), artefactFile.path().toString().replace("\\", "/"));
        }
    }
}
