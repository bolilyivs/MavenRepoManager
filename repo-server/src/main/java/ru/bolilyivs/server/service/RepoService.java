package ru.bolilyivs.server.service;

import ru.bolilyivs.server.data.dto.RepoDto;

import java.util.List;

public interface RepoService {
    RepoDto get(String id);

    List<RepoDto> list();

    void create(RepoDto repoDto);

    void update(String id, RepoDto repoDto);

    void delete(String id);
}
