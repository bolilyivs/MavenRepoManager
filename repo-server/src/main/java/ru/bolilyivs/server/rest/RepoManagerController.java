package ru.bolilyivs.server.rest;

import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.data.dto.RepoUpdateDto;
import ru.bolilyivs.server.service.RepoService;

import java.util.List;

/*
 * GET /api/v1/repo/{id} -получаем информацию
 * POST /api/v1/repo/ -Добавляем
 * PATCH /api/v1/repo/{id} - Изменяем
 * DELETE /api/v1/repo/{id} - удаляем физически
 */

@Controller("/api/v1/repo/manager")
@RequiredArgsConstructor
@Tag(name = "ManagerController")
public class RepoManagerController {

    private final RepoService repoService;

    @Get(uri = "/{id}")
    public RepoDto get(@PathVariable String id) {
        return repoService.get(id);
    }

    @Get
    public List<RepoDto> list() {
        return repoService.list();
    }

    @Post
    public void create(@Body RepoDto repoDto) {
        repoService.create(repoDto);
    }

    @Put(uri = "/{id}")
    public void update(@PathVariable String id, @Body RepoUpdateDto repoDto) {
        repoService.update(id, repoDto);
    }

    @Delete(uri = "/{id}")
    public void remove(@PathVariable String id) {
        repoService.delete(id);
    }
}
