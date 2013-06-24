package cz.muni.fi.pv243.et.test;

import java.io.File;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class Deployments {

    public static EnterpriseArchive mainDeployment(Class<?>... classes) {
        EnterpriseArchive ear = ShrinkWrap.create(ZipImporter.class, "test.ear").importFrom(new File("../project-et-ear/target/project-et-ear.ear")).as(EnterpriseArchive.class);
        WebArchive war = ear.getAsType(WebArchive.class, "project-et-web.war");
        for (Class<?> cls : classes) {
            war.addClass(cls);
        }
        return ear;

    }
}
