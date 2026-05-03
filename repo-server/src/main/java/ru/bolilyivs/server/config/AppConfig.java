package ru.bolilyivs.server.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("app")
public class AppConfig {
    private String cacheDir;
    private String rootRepoDir;
}
