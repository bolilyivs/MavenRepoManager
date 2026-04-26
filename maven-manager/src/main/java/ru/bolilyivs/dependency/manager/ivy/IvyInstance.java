package ru.bolilyivs.dependency.manager.ivy;

import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

import java.util.List;

public interface IvyInstance {
    ResolveReport resolve(ArtefactId metaData);

    List<String> listArtefactId(String groupId);

    List<String> listVersion(String groupId, String artefactId);
}
