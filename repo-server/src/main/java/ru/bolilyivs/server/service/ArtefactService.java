package ru.bolilyivs.server.service;

import ru.bolilyivs.dependency.manager.model.artefact.Artefact;

import java.util.Optional;

public interface ArtefactService {
    Optional<Artefact> findById(String artefactId);

    Optional<Artefact> findByIdWithoutDependecies(String artefactId);

    Optional<Artefact> findByIdWithoutFiles(String artefactId);

    void save(Artefact artefact);
}
