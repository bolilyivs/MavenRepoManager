package ru.bolilyivs.dependency.manager.model.dependency;

import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Dependency(
        ArtefactId artefactId,
        List<Dependency> dependencies
) {
    public Set<Dependency> getFlatListDependencies() {
        Set<Dependency> flatListDependencies = new HashSet<>();
        flatListDependencies.add(this);
        flatListDependencies.addAll(dependencies.stream()
                .map(Dependency::getFlatListDependencies)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
        );
        return flatListDependencies;
    }

    public String printTree(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append("|%s>%s\n".formatted(tab, this.artefactId));
        for (Dependency dependency : dependencies()) {
            sb.append(dependency.printTree("%s%s".formatted(tab, "-")));
        }
        return sb.toString();
    }
}
