package ru.bolilyivs.dependency.manager.service;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Consumer;

public interface MavenArtefactDownloader {
    Path downloadArtefactToFile(Repository repository, ArtefactFile artefactFile);

    void downloadArtefact(Repository repository, ArtefactFile artefactFile, Consumer<InputStream> process);

}
