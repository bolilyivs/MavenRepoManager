package ru.bolilyivs.dependency.manager.ivy.impl;

import lombok.Getter;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.resolver.IBiblioResolver;
import org.apache.ivy.plugins.resolver.URLResolver;
import ru.bolilyivs.dependency.manager.ivy.IvyConfig;

import java.io.File;

@Getter
public class IvyConfigImpl implements IvyConfig {
    private final static String[] CONFS = new String[]{"default", "sources", "javadoc"};

    private final String repoName;
    private final String repoUrl;
    private final String localRepoPath;

    private final URLResolver urlResolver;
    private final ResolveOptions resolveOptions;
    private final IvySettings settings;

    public IvyConfigImpl(String repoName, String repoUrl, String localRepoPath) {
        this.repoName = repoName;
        this.repoUrl = repoUrl;
        this.localRepoPath = localRepoPath;

        this.urlResolver = createURLResolver();
        this.settings = createSetting();
        this.resolveOptions = createResolveOptions();
    }

    public static IvyConfigImpl of(String repoName, String repoUrl, String localRepoPath) {
        return new IvyConfigImpl(repoName, repoUrl, localRepoPath);
    }

    private URLResolver createURLResolver() {
        IBiblioResolver br = new IBiblioResolver();
        br.setRoot(repoUrl);
        br.setM2compatible(true);
        br.setUsepoms(true);
        br.setName(repoName);
        br.setUseMavenMetadata(true);
        return br;
    }

    private IvySettings createSetting() {
        IvySettings ivySettings = new IvySettings();
        File cacheDir = new File("%s/%s/cache".formatted(localRepoPath, repoName)).getAbsoluteFile();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        ivySettings.setDefaultCache(cacheDir);
        ivySettings.addResolver(urlResolver);
        ivySettings.setDefaultResolver(urlResolver.getName());
        return ivySettings;
    }

    private ResolveOptions createResolveOptions() {
        ResolveOptions ro = new ResolveOptions();
        ro.setDownload(false);
        ro.setConfs(CONFS);
        ro.setOutputReport(false);
        return ro;
    }
}
