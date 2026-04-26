package ru.bolilyivs.dependency.manager.ivy;

import org.apache.ivy.core.report.ResolveReport;
import ru.bolilyivs.dependency.manager.model.artefact.ArtefactMetaData;

public interface IvyInstance {
    ResolveReport resolve(ArtefactMetaData metaData);
}
