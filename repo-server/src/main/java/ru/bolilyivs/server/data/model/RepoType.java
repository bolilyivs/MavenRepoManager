package ru.bolilyivs.server.data.model;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

@Serdeable
@Schema(name = "RepoType", description = "Тип репозитория")
public enum RepoType {
    REMOTE, LOCAL;
}
