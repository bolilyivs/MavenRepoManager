package ru.bolilyivs.dependency.manager;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;

import java.io.InputStream;

public interface MavenArtefactDownloader {
    String downloadArtefactToFile(Repository repository, ArtefactFile artefactFile);

    InputStream downloadArtefact(Repository repository, ArtefactFile artefactFile);

}
