package cz.muni.fi.pv243.et.ftest.util;

import java.io.File;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;

public class Deployments {

    public static EnterpriseArchive mainDeployment(Class<?>... classes) {
        return ShrinkWrap
                .create(ZipImporter.class, "test.ear")
                .importFrom(new File("../project-et-ear/target/project-et-ear.ear"))
                .as(EnterpriseArchive.class);
    }
}
