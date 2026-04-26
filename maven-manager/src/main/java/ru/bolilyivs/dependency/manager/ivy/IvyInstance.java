package ru.bolilyivs.dependency.manager.ivy;

import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactId;

public interface IvyInstance {
    ResolveReport resolve(ArtefactId metaData);
}
