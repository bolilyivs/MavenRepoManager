package ru.bolilyivs.server.data.mapper;

import jakarta.inject.Singleton;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.server.data.model.ArtefactEntity;
import ru.bolilyivs.server.data.model.ArtefactFileEntity;

import java.nio.file.Path;
import java.util.HashMap;

@Singleton
public class ArtefactMapper {

    public ArtefactEntity mapFrom(Artefact artefact) {
        return mapFrom(artefact, new HashMap<>());
    }

    private ArtefactEntity mapFrom(Artefact artefact, HashMap<String, ArtefactEntity> cache) {
        ArtefactEntity entity = createArtefactEntity(artefact, cache);
        artefact.getDependencies()
                .stream()
                .map(dep -> mapFrom(dep, cache))
                .forEach(entity::addDependency);
        return entity;
    }

    private ArtefactEntity createArtefactEntity(Artefact artefact, HashMap<String, ArtefactEntity> cache) {
        return cache.computeIfAbsent(artefact.getId().toString(), (_) -> {
            ArtefactEntity entity = new ArtefactEntity();
            entity.setArtefactId(artefact.getId().toString());
            artefact.getFiles()
                    .stream()
                    .map(this::mapFrom)
                    .forEach(entity::addFile);
            return entity;
        });
    }

    public ArtefactFileEntity mapFrom(ArtefactFile file) {
        ArtefactFileEntity entity = new ArtefactFileEntity();
        entity.setFilename(file.filename());
        entity.setType(file.type());
        entity.setPath(file.path().toString().replace("\\", "/"));
        return entity;
    }

    public Artefact mapFrom(ArtefactEntity entity, boolean withDependency, boolean withFiles) {
        Artefact artefact = Artefact.of(ArtefactId.of(entity.getArtefactId()));

        if (withFiles) {
            artefact.setFiles(
                    entity.getFiles()
                            .stream()
                            .map(this::mapFrom)
                            .toList()
            );
        }

        if (withDependency) {
            artefact.setDependencies(
                    entity.getDependency()
                            .stream()
                            .map(dep -> mapFrom(dep, withDependency, withFiles))
                            .toList()
            );
        }
        return artefact;
    }

    public ArtefactFile mapFrom(ArtefactFileEntity entity) {
        return new ArtefactFile(
                entity.getFilename(),
                entity.getType(),
                Path.of(entity.getPath())
        );
    }
}
