package ru.bolilyivs.server.service;

public interface DownloadService {
    void downloadArtifactWithDependencies(String repoName, String dependecyString);

    void downloadArtifact(String repoName, String dependecyString);
}
