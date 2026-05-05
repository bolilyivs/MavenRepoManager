package ru.bolilyivs.server.service;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;
import ru.bolilyivs.server.config.AppConfig;
import ru.bolilyivs.server.data.exception.RepoException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final AppConfig appConfig;

    @Override
    public Path uploadArtefact(String repoName, String dependecyString, CompletedFileUpload file) {

        String filename = file.getFilename();
        ArtefactFile artefactFile = ArtefactFile.of(
                filename,
                ArtefactFileType.ofFileName(filename),
                ArtefactId.of(dependecyString)
        );
        Path targetPath = createTargetPath(repoName, artefactFile);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            throw new RepoException("Ошибка во время загрузки файла в репозиторий", HttpStatus.INTERNAL_SERVER_ERROR, ioException);
        }

        return targetPath;
    }

    private Path createTargetPath(String repoName, ArtefactFile artefactFile) {
        Path targetPath = Path.of(appConfig.getRootRepoDir(), repoName, artefactFile.path().toString().replace("\\", "/"));
        try {
            Files.createDirectories(targetPath.getParent());
        } catch (IOException ioException) {
            throw new RepoException("Ошибка при создании директорий", HttpStatus.INTERNAL_SERVER_ERROR, ioException);
        }
        return targetPath;
    }
}
