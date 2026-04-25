package ru.bolilyivs.server.service;

import io.micronaut.http.multipart.CompletedFileUpload;

public interface UploadService {
    String uploadArtefact(String repoName,
                          String dependecyString,
                          CompletedFileUpload file);
}
