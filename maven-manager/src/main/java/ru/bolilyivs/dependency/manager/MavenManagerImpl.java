package ru.bolilyivs.dependency.manager;

import lombok.RequiredArgsConstructor;
import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.dependency.manager.service.MavenArtefactDownloader;
import ru.bolilyivs.dependency.manager.service.MavenArtefactFileFinder;
import ru.bolilyivs.dependency.manager.service.MavenArtefactIdFinder;
import ru.bolilyivs.dependency.manager.service.MavenDependencyFinder;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MavenManagerImpl implements MavenManager {

    private final MavenArtefactIdFinder mavenArtefactIdFinder;
    private final MavenDependencyFinder mavenDependencyFinder;
    private final MavenArtefactFileFinder mavenArtefactFileFinder;
    private final MavenArtefactDownloader mavenArtefactDownloader;

    @Override
    public Artefact findArtefactWithFilesAndDependencies(Repository repository, ArtefactId id) {
        Artefact artefact = resolveDependency(repository, id);
        Set<Artefact> artefacts = artefact.getFlatListDependencies();
        artefacts.forEach(art -> {
            List<ArtefactFile> artefactFiles = findArtefactFiles(repository, art.getId());
            art.setFiles(artefactFiles);
        });
        return artefact;
    }

    @Override
    public Artefact findArtefactWithFiles(Repository repository, ArtefactId id) {
        List<ArtefactFile> artefactFiles = findArtefactFiles(repository, id);
        return Artefact.ofArtefactFiles(id, artefactFiles);
    }

    @Override
    public List<ArtefactFile> findArtefactFiles(Repository repository, ArtefactId id) {
        return mavenArtefactFileFinder.find(repository, id);
    }

    @Override
    public Artefact resolveDependency(Repository repository, ArtefactId id) {
        return mavenDependencyFinder.resolve(repository, id);
    }

    @Override
    public Path downloadArtefactToFile(Repository repository, ArtefactFile artefactFile) {
        return mavenArtefactDownloader.downloadArtefactToFile(repository, artefactFile);
    }

    @Override
    public InputStream downloadArtefactWithInputStream(Repository repository, ArtefactFile artefactFile) {
        return mavenArtefactDownloader.downloadArtefact(repository, artefactFile);
    }

    @Override
    public List<String> listArtefactId(Repository repository, String groupId) {
        return mavenArtefactIdFinder.listArtefactId(repository, groupId);
    }

    @Override
    public List<String> listVersion(Repository repository, String groupId, String artefactId) {
        return mavenArtefactIdFinder.listVersion(repository, groupId, artefactId);
    }
}
