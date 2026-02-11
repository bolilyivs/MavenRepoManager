package model.dependency;

import model.artefact.Artefact;

import java.util.List;

public record Dependency(
        Artefact artefact,
        List<Dependency> dependencies
) {
}
