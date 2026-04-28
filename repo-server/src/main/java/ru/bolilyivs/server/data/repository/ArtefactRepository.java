package ru.bolilyivs.server.data.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import ru.bolilyivs.server.data.model.ArtefactEntity;

@Repository
public interface ArtefactRepository extends JpaRepository<ArtefactEntity, String> {

}
