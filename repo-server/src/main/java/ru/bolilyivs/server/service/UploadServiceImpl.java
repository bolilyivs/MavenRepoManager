package ru.bolilyivs.server.service;

import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;
import ru.bolilyivs.server.config.AppConfig;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Singleton
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final AppConfig appConfig;

    @Override
    @SneakyThrows
    public String uploadArtefact(String repoName, String dependecyString, CompletedFileUpload file) {

        String filename = file.getFilename();
        ArtefactFile artefactFile = ArtefactFile.of(
                filename,
                ArtefactFileType.ofFileName(filename),
                ArtefactMetaData.of(dependecyString)
        );
        Path targetPath = createTargetPath(repoName, artefactFile);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return targetPath.toString();
    }

    @SneakyThrows
    private Path createTargetPath(String repoName, ArtefactFile artefactFile) {
        Path targetPath = Path.of(appConfig.getRootRepoDir(), repoName, artefactFile.path().toString().replace("\\", "/"));
        Files.createDirectories(targetPath.getParent());
        return targetPath;
    }
}
