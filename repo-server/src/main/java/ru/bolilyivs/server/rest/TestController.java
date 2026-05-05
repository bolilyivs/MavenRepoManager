package ru.bolilyivs.server.rest;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.uri.UriBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.server.config.AppConfig;
import ru.bolilyivs.server.data.dto.MessageDto;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.service.FindService;
import ru.bolilyivs.server.service.RepoService;

import java.nio.file.Path;
import java.util.List;

@Controller("/api/v1/test/")
@RequiredArgsConstructor
@Tag(name = "TestController", description = "Ручное тестирование")
public class TestController {

    private final AppConfig appConfig;
    private final RepoService repoService;
    private final FindService findService;

    @Get(uri = "repo/{repoName}/find/artefactId")
    @Operation(summary = "Найти ИД артефакта",
            description = "Найти ИД артефакта"
    )
    public MessageDto<List<String>> findArtefactId(@PathVariable String repoName, @Parameter String groupId) {
        return MessageDto.of(findService.findArtefactId(repoName, groupId));
    }

    @Get(uri = "repo/{repoName}/find/version")
    @Operation(summary = "Найти версию артефакта",
            description = "Найти версию артефакта"
    )
    public MessageDto<List<String>> findVersion(@PathVariable String repoName,
                                                @Parameter String groupId,
                                                @Parameter String artefactId) {
        return MessageDto.of(findService.findVersion(repoName, groupId, artefactId));
    }

    @Get(uri = "repo/{repoName}/path/local")
    @Operation(summary = "Получить локальный путь к артефакту",
            description = "Получить локальный путь к артефакту"
    )
    public MessageDto<String> getLocalPath(
            @PathVariable String repoName,
            @Parameter String artefactIdString,
            @Parameter ArtefactFileType artefactFileType) {
        RepoDto repoDto = repoService.get(repoName);
        ArtefactId artefactId = ArtefactId.of(artefactIdString);
        ArtefactFile artefactFile = ArtefactFile.generateFrom(artefactId, artefactFileType);
        String path = Path.of(appConfig.getRootRepoDir(), repoDto.name(), artefactFile.path().toString()).toString();
        return MessageDto.of(path.replace("\\", "/"));
    }

    @Get(uri = "repo/{repoName}/path/remote")
    @Operation(summary = "Получить удалённый путь к артефакту",
            description = "Получить удалённый путь к артефакту"
    )
    public MessageDto<String> getRemotePath(
            @PathVariable String repoName,
            @Parameter String artefactIdString,
            @Parameter ArtefactFileType artefactFileType) {
        RepoDto repoDto = repoService.get(repoName);
        ArtefactId artefactId = ArtefactId.of(artefactIdString);
        ArtefactFile artefactFile = ArtefactFile.generateFrom(artefactId, artefactFileType);
        String path = UriBuilder.of(repoDto.url())
                .path(artefactFile.path().toString().replace("\\", "/"))
                .build()
                .toString();
        return MessageDto.of(path);
    }
}
