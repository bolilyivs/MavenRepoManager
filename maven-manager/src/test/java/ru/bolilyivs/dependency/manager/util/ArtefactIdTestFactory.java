package ru.bolilyivs.dependency.manager.util;

import lombok.experimental.UtilityClass;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

@UtilityClass
public class ArtefactIdTestFactory {
    private static final String ARTEFACT_STRING = "org.apache.commons:commons-text:1.15.0";

    public static ArtefactId createArtefactIdTest() {
        return ArtefactId.of(ARTEFACT_STRING);
    }
}
