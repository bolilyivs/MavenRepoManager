package ru.bolilyivs.dependency.manager;

import ru.bolilyivs.dependency.manager.model.Repository;
import ru.bolilyivs.dependency.manager.model.artefact.Artefact;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFile;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.nio.file.Path;
import java.util.List;

/**
 * Управление взаимодействием с Maven-репозиториями.
 * Предоставляет методы для поиска артефактов, разрешения зависимостей,
 * скачивания файлов и получения метаданных репозитория.
 */
public interface MavenManager {

    /**
     * Находит артефакт в указанном репозитории вместе с его файлами и зависимостями.
     *
     * @param repository репозиторий, в котором выполняется поиск
     * @param id         идентификатор артефакта
     * @return объект {@link Artefact}, содержащий метаданные, файлы и зависимости,
     * или {@code null}, если артефакт не найден
     */
    Artefact findArtefactWithFilesAndDependencies(Repository repository, ArtefactId id);

    /**
     * Находит артефакт в указанном репозитории вместе с его файлами.
     * Зависимости при этом не загружаются.
     *
     * @param repository репозиторий, в котором выполняется поиск
     * @param id         идентификатор артефакта
     * @return объект {@link Artefact} с файлами, или {@code null}, если артефакт не найден
     */
    Artefact findArtefactWithFiles(Repository repository, ArtefactId id);

    /**
     * Получает список файлов, ассоциированных с указанным артефактом.
     *
     * @param repository репозиторий для поиска
     * @param id         идентификатор артефакта
     * @return список {@link ArtefactFile}. Возвращает пустой список, если у артефакта нет файлов.
     */
    List<ArtefactFile> findArtefactFiles(Repository repository, ArtefactId id);

    /**
     * Разрешает зависимость для указанного артефакта.
     * Выполняет поиск целевого артефакта в репозитории и возвращает его с учётом
     * правил разрешения версий и транзитивных зависимостей.
     *
     * @param repository репозиторий для разрешения зависимости
     * @param id         идентификатор артефакта-зависимости
     * @return разрешённый {@link Artefact}, или {@code null}, если зависимость не найдена
     */
    Artefact resolveDependency(Repository repository, ArtefactId id);

    /**
     * Скачивает указанный файл артефакта из репозитория в локальную файловую систему.
     *
     * @param repository   репозиторий, из которого происходит скачивание
     * @param artefactFile файл артефакта для скачивания
     * @return {@link Path} к локальному файлу после успешного скачивания
     */
    Path downloadArtefactToFile(Repository repository, ArtefactFile artefactFile);

    /**
     * Получает список идентификаторов артефактов, доступных в указанной группе.
     *
     * @param repository репозиторий для поиска
     * @param groupId    идентификатор группы (например, {@code org.apache.maven})
     * @return список строк с идентификаторами артефактов. Возвращает пустой список, если группа отсутствует.
     */
    List<String> listArtefactId(Repository repository, String groupId);

    /**
     * Получает список доступных версий для указанного артефакта в заданной группе.
     *
     * @param repository репозиторий для поиска
     * @param groupId    идентификатор группы
     * @param artefactId идентификатор артефакта
     * @return список строк с номерами версий. Возвращает пустой список, если артефакт не найден.
     */
    List<String> listVersion(Repository repository, String groupId, String artefactId);
}