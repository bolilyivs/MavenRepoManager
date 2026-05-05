package ru.bolilyivs.dependency.manager.ivy;

import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.resolver.URLResolver;

public interface IvyConfig {
    String getRepoName();

    String getRepoUrl();

    String getLocalRepoPath();


    URLResolver getUrlResolver();

    ResolveOptions getResolveOptions();

    IvySettings getSettings();
}
