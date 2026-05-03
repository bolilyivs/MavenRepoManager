package ru.bolilyivs.server.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "repo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repo {
    @Id
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    String name;
    @Column(name = "url")
    String url;
    @Column(name = "repo_type", nullable = false)
    @Enumerated(EnumType.STRING)
    RepoType repoType;
}
