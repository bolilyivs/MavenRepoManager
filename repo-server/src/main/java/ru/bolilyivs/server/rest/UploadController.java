package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.MessageDto;
import ru.bolilyivs.server.service.UploadService;

import java.nio.file.Path;

@Controller("/api/v1/repo")
@RequiredArgsConstructor
@Tag(name = "UploadController")
public class UploadController {

    private final UploadService uploadService;

    @Post(uri = "{repoName}/upload/artefact", consumes = MediaType.MULTIPART_FORM_DATA)
    public MessageDto<String> dependencies(@PathVariable String repoName,
                                           @Parameter String dependecyString,
                                           CompletedFileUpload file) {
        Path path = uploadService.uploadArtefact(repoName, dependecyString, file);
        return MessageDto.of(path.toString());
    }
}
