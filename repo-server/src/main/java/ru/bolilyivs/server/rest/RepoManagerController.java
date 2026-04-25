package ru.bolilyivs.server.rest;

import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "ManagerController")
public class RepoManagerController {

    private final RepoService repoService;

    @Get(uri = "{repoName}")
    public RepoDto get(@PathVariable String repoName) {
        return repoService.get(repoName);
    }

    @Get
    public List<RepoDto> list() {
        return repoService.list();
    }

    @Post
    public void create(@Body RepoDto repoDto) {
        repoService.create(repoDto);
    }

    @Put(uri = "{repoName}")
    public void update(@PathVariable String repoName, @Body RepoUpdateDto repoDto) {
        repoService.update(repoName, repoDto);
    }

    @Delete(uri = "{repoName}")
    public void remove(@PathVariable String repoName) {
        repoService.delete(repoName);
    }
}
