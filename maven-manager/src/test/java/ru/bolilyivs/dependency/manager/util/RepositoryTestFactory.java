package ru.bolilyivs.dependency.manager.util;

import lombok.experimental.UtilityClass;
import ru.bolilyivs.dependency.manager.model.Repository;

@UtilityClass
public class RepositoryTestFactory {
    private static final String REPO_URL = "https://repo1.maven.org/maven2";
    private static final String REPO_NAME = "central";

    public static Repository createCentralTestRepository() {
        return new Repository(REPO_NAME, REPO_URL);
    }
}
