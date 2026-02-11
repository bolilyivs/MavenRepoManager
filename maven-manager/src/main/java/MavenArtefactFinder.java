import model.artefact.Artefact;
import model.artefact.ArtefactMetaData;
import model.dependency.Dependency;

public interface MavenArtefactFinder {
    Artefact findArtefact(String repoBaseUrl, ArtefactMetaData metaData);

    Dependency findDependency(String repoBaseUrl, ArtefactMetaData metaData);
}
