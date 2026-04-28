package ru.bolilyivs.dependency.manager;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.nio.file.Path;
import java.util.List;

public interface MavenManager {

    Artefact findArtefactWithFilesAndDependencies(Repository repository, ArtefactId id);

    Artefact findArtefactWithFiles(Repository repository, ArtefactId id);

    List<ArtefactFile> findArtefactFiles(Repository repository, ArtefactId id);

    Artefact resolveDependency(Repository repository, ArtefactId id);

    Path downloadArtefactToFile(Repository repository, ArtefactFile artefactFile);

    List<String> listArtefactId(Repository repository, String groupId);

    List<String> listVersion(Repository repository, String groupId, String artefactId);
}
