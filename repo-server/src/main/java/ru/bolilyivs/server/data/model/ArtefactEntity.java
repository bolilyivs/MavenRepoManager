package ru.bolilyivs.server.data.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artefact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "artefactId")
public class ArtefactEntity {

    @Id
    @NotNull
    @Column(name = "artefact_id")
    private String artefactId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "dependency",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "artefact_id")
    )
    private Set<ArtefactEntity> dependency = new HashSet<>();

    @OneToMany(mappedBy = "artefact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArtefactFileEntity> files = new HashSet<>();

    public void addFile(ArtefactFileEntity file) {
        file.setArtefact(this);
        files.add(file);
    }

    public void addDependency(ArtefactEntity dep) {
        dependency.add(dep);
    }
}
