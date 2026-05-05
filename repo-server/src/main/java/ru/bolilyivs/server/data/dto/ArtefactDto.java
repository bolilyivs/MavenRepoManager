package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.List;

@Serdeable
@Schema(name = "ArtefactDto", description = "Зависимость")
public record ArtefactDto(
        @Schema(description = "Организация", example = "org.apache.commons")
        String groupId,
        @Schema(description = "Название артефакта", example = "commons-text")
        String artifactId,
        @Schema(description = "Версия артефакта", example = "1.15.0")
        String version,
        @Schema(description = "Файлы артефакта")
        List<ArtefactFileDto> files,
        @Schema(description = "Зависимости артефакта")
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
