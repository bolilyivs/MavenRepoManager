package ru.bolilyivs.dependency.manager.service;

import ru.bolilyivs.dependency.manager.model.Repository;

import java.util.List;

public interface MavenArtefactIdFinder {

    List<String> listArtefactId(Repository repository, String groupId);

    List<String> listVersion(Repository repository, String groupId, String artefactId);
}
