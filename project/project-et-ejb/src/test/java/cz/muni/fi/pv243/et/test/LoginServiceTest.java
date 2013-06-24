package cz.muni.fi.pv243.et.test;

import cz.muni.fi.pv243.et.service.ExpenseReportService;
import cz.muni.fi.pv243.et.service.LoginService;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.picketlink.Identity;
import org.picketlink.authentication.AuthenticationException;

/**
 *
 * @author rsmeral
 */
@RunWith(Arquillian.class)
public class LoginServiceTest {

    @Inject
    private LoginService loginService;

    @Inject
    private ExpenseReportService expenseReports;

    @Inject
    private Identity identity;

    @Deployment(testable = true)
    public static EnterpriseArchive deployment() {
        return Deployments.mainDeployment(LoginServiceTest.class);
    }

    @Test
    @InSequence(1)
    public void testLoginLogout() throws AuthenticationException {
        loginService.login("admin", "admin");
        assertTrue("User should be logged in", identity.isLoggedIn());
        identity.logout();
        assertFalse("User should be logged out", identity.isLoggedIn());
    }

    @Test
    @InSequence(2)
    public void testLoginFailed() throws AuthenticationException {
        loginService.login("admin", "wrongPassword");
        assertFalse("User should not be logged in with bad password", identity.isLoggedIn());
    }
    
}
