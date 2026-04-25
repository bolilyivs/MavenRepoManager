package ru.bolilyivs.server.service;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.data.dto.RepoUpdateDto;
import ru.bolilyivs.server.data.model.Repo;
import ru.bolilyivs.server.data.repository.RepoRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService {

    private final RepoRepository repoRepository;

    @Override
    public RepoDto get(String id) {
        return repoRepository.findById(id)
                .map(this::toRepoDto)
                .orElseThrow();
    }

    @Override
    public List<RepoDto> list() {
        return repoRepository.findAll().stream().map(this::toRepoDto).toList();
    }

    @Override
    public void create(RepoDto repoDto) {
        Repo repo = toRepo(repoDto);
        repoRepository.save(repo);
    }

    @Override
    public void update(String id, RepoUpdateDto repoDto) {
        Repo repoOrigin = repoRepository.findById(id).orElseThrow();
        repoOrigin.setUrl(repoDto.url());
        repoOrigin.setRepoType(repoDto.repoType());
        repoRepository.save(repoOrigin);
    }

    @Override
    public void delete(String id) {
        repoRepository.deleteById(id);
    }

    private RepoDto toRepoDto(Repo repo) {
        return new RepoDto(repo.getName(), repo.getUrl(), repo.getRepoType());
    }

    private Repo toRepo(RepoDto dto) {
        return new Repo(dto.name(), dto.url(), dto.repoType());
    }
}
