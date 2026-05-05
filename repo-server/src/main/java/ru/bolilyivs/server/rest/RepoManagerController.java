package ru.bolilyivs.server.rest;

import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.data.dto.RepoUpdateDto;
import ru.bolilyivs.server.service.RepoService;

import java.util.List;

/*
 * GET /api/v1/repo/{repoName} -получаем информацию
 * POST /api/v1/repo/ -Добавляем
 * PATCH /api/v1/repo/{repoName} - Изменяем
 * DELETE /api/v1/repo/{repoName} - удаляем физически
 */

@Controller("/api/v1/repo")
@RequiredArgsConstructor
@Tag(name = "RepoManagerController", description = "Управление репозиториями")
public class RepoManagerController {

    private final RepoService repoService;

    @Get(uri = "{repoName}")
    @Operation(summary = "Получить репозиторий",
            description = "Получить репозиторий по его названию"
    )
    public RepoDto get(@PathVariable String repoName) {
        return repoService.get(repoName);
    }

    @Get
    @Operation(summary = "Получить список репозиториев",
            description = "Получить список репозиториев"
    )
    public List<RepoDto> list() {
        return repoService.list();
    }

    @Post
    @Operation(summary = "Создать репозиторий",
            description = "Создать репозиторий"
    )
    public void create(@Body @Valid RepoDto repoDto) {
        repoService.create(repoDto);
    }

    @Put(uri = "{repoName}")
    @Operation(summary = "Обновить репозиторий",
            description = "Обновить репозиторий"
    )
    public void update(@PathVariable String repoName, @Body @Valid RepoUpdateDto repoDto) {
        repoService.update(repoName, repoDto);
    }

    @Delete(uri = "{repoName}")
    @Operation(summary = "Удалить репозиторий",
            description = "Удалить репозиторий"
    )
    public void remove(@PathVariable String repoName) {
        repoService.delete(repoName);
    }
}
