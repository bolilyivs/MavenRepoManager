package ru.bolilyivs.server.service;

import java.nio.file.Path;

public interface RepoProxyService {
    Path getFile(String repoName, String artefactPath);
}
