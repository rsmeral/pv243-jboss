package cz.muni.fi.pv243.et.test;

import cz.muni.fi.pv243.et.service.ExpenseReportService;
import cz.muni.fi.pv243.et.service.LoginService;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;

@RunWith(Arquillian.class)
public class ExpenseServiceTest {
    
    @Inject
    private LoginService loginService;

    @Inject
    private ExpenseReportService expenseReports;

    @Inject
    private Identity identity;

    @Deployment(testable = true)
    public static EnterpriseArchive deployment() {
        return Deployments.mainDeployment(ExpenseServiceTest.class);
    }

    @Test
    @InSequence(1)
    public void testLoggedIn() throws AuthenticationException {
        loginService.login("admin", "admin");
        assertTrue(identity.isLoggedIn());
    }

    @Test
    @InSequence(2)
    public void testFindAllReports() {
        assertEquals(3, expenseReports.findAll().size());
    }
}
