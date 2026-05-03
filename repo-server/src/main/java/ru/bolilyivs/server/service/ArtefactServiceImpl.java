package ru.bolilyivs.server.service;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.server.data.mapper.ArtefactMapper;
import ru.bolilyivs.server.data.model.ArtefactEntity;
import ru.bolilyivs.server.data.repository.ArtefactRepository;

import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class ArtefactServiceImpl implements ArtefactService {

    private final ArtefactRepository artefactRepository;
    private final ArtefactMapper mapper;

    @Override
    @Transactional
    public Optional<Artefact> findById(String artefactId) {
        return artefactRepository.findById(artefactId)
                .map(entity -> mapper.mapFrom(entity, true, true));
    }

    @Override
    @Transactional
    public Optional<Artefact> findByIdWithoutDependecies(String artefactId) {
        return artefactRepository.findById(artefactId)
                .map(entity -> mapper.mapFrom(entity, false, true));
    }

    @Override
    @Transactional
    public Optional<Artefact> findByIdWithoutFiles(String artefactId) {
        return artefactRepository.findById(artefactId)
                .map(entity -> mapper.mapFrom(entity, true, false));
    }

    @Override
    public void save(Artefact artefact) {
        ArtefactEntity entity = mapper.mapFrom(artefact);
        artefactRepository.save(entity);
    }

}
