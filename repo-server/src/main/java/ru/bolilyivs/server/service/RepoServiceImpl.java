package ru.bolilyivs.server.service;

import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.server.data.dto.RepoDto;
import ru.bolilyivs.server.data.dto.RepoUpdateDto;
import ru.bolilyivs.server.data.exception.RepoException;
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
                .orElseThrow(() -> RepoException.ofNotFound("Repo", id));
    }

    @Override
    public List<RepoDto> list() {
        return repoRepository.findAll().stream().map(this::toRepoDto).toList();
    }

    @Override
    public void create(RepoDto repoDto) {
        if (repoRepository.existsById(repoDto.name())) {
            throw new RepoException("Репозиторий с таким именем уже существует", HttpStatus.BAD_REQUEST);
        }

        Repo repo = toRepo(repoDto);
        repoRepository.save(repo);
    }

    @Override
    public void update(String id, RepoUpdateDto repoDto) {
        Repo repoOrigin = repoRepository.findById(id).orElseThrow(() -> RepoException.ofNotFound("Repo", id));
        repoOrigin.setUrl(repoDto.url());
        repoOrigin.setRepoType(repoDto.repoType());
        repoRepository.update(repoOrigin);
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
