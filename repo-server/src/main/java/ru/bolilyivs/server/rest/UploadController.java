package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.MessageDto;
import ru.bolilyivs.server.service.UploadService;

import java.nio.file.Path;

@Controller("/api/v1/repo")
@RequiredArgsConstructor
@Tag(name = "UploadController", description = "Загрузка артефакта на локальный репозиторий")
public class UploadController {

    private final UploadService uploadService;

    @Post(uri = "{repoName}/upload/artefact/file", consumes = MediaType.MULTIPART_FORM_DATA)
    @Operation(summary = "Загрузить артефакт вручную",
            description = "Загрузить артефакт вручную"
    )
    public MessageDto<String> artefactFile(@PathVariable String repoName,
                                           @Parameter String moduleDependency,
                                           CompletedFileUpload file) {
        Path path = uploadService.uploadArtefact(repoName, moduleDependency, file);
        return MessageDto.of(path.toString());
    }
}
