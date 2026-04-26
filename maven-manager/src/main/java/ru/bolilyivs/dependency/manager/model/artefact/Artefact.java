package ru.bolilyivs.dependency.manager.model.artefact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Artefact {
    private final ArtefactId id;
    private List<Artefact> dependencies = new ArrayList<>();
    private List<ArtefactFile> files = new ArrayList<>();

    public static Artefact of(ArtefactId artefactId) {
        return new Artefact(artefactId);
    }

    public static Artefact ofDependencies(ArtefactId artefactId, List<Artefact> dependencies) {
        return new Artefact(artefactId, dependencies, new ArrayList<>());
    }

    public static Artefact ofArtefactFiles(ArtefactId artefactId, List<ArtefactFile> artefactFiles) {
        return new Artefact(artefactId, new ArrayList<>(), artefactFiles);
    }

    public static Artefact of(ArtefactId artefactId, List<Artefact> dependencies, List<ArtefactFile> artefactFiles) {
        return new Artefact(artefactId, dependencies, artefactFiles);
    }

    public Set<Artefact> getFlatListDependencies() {
        Set<Artefact> flatListDependencies = new HashSet<>();
        flatListDependencies.add(this);
        flatListDependencies.addAll(dependencies.stream()
                .map(Artefact::getFlatListDependencies)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
        );
        return flatListDependencies;
    }

    public String printTree(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append("|%s>%s\n".formatted(tab, this.getId()));
        for (Artefact dependency : getDependencies()) {
            sb.append(dependency.printTree("%s%s".formatted(tab, "-")));
        }
        return sb.toString();
    }
}
