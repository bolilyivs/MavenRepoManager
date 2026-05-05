package ru.bolilyivs.server.data.model;


import jakarta.persistence.*;
import lombok.*;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactFileType;

@Entity
@Table(name = "artefact_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"artefact", "filename"})
public class ArtefactFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "artefact_file_seq", sequenceName = "artefact_file_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artefact")
    private ArtefactEntity artefact;

    @Column(name = "filename")
    private String filename;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ArtefactFileType type;

    @Column(name = "path")
    private String path;
}
