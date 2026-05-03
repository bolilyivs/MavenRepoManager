package ru.bolilyivs.server.service;

import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.data.dto.RepoUpdateDto;

import java.util.List;

public interface RepoService {
    RepoDto get(String id);

    List<RepoDto> list();

    void create(RepoDto repoDto);

    void update(String id, RepoUpdateDto repoDto);

    void delete(String id);
}
