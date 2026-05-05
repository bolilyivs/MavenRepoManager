package ru.bolilyivs.server.service;

import io.micronaut.http.multipart.CompletedFileUpload;

import java.nio.file.Path;

public interface UploadService {
    Path uploadArtefact(String repoName,
                        String dependecyString,
                        CompletedFileUpload file);
}
