package ru.bolilyivs.server.service;

public interface DownloadService {
    void downloadArtifact(String repoName, String dependecyString);
}
