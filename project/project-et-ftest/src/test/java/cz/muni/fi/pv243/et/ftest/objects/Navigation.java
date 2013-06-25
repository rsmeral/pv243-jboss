package cz.muni.fi.pv243.et.ftest.objects;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.WebElement;

public class Navigation {

    @FindBy(id = "nav-home")
    WebElement navHome;

    @FindBy(id = "nav-init")
    WebElement navInit;

    @FindBy(id = "nav-reports")
    WebElement navReports;

    @FindBy(id = "nav-purposes")
    WebElement navPurposes;

    @FindBy(id = "nav-receipts")
    WebElement navReceipts;

    @FindBy(id = "nav-users")
    WebElement navUsers;

    @FindBy(id = "nav-identity")
    WebElement navIdentity;

    @FindBy(id = "nav-drop-edit")
    WebElement navUserEdit;

    @FindBy(id = "nav-drop-change-password")
    WebElement navUserChangePassword;

    @FindBy(id = "nav-drop-logout")
    WebElement navUserLogout;

    public void home() {
        Graphene.guardHttp(navHome).click();
    }
    
    public void init() {
        Graphene.guardHttp(navInit).click();
    }
    
    public void reports() {
        Graphene.guardHttp(navReports).click();
    }
    
    public void purposes() {
        Graphene.guardHttp(navPurposes).click();
    }
    
    public void receipts() {
        Graphene.guardHttp(navReceipts).click();
    }
    
    public void users() {
        Graphene.guardHttp(navUsers).click();
    }
    
    public void identity() {
        Graphene.guardHttp(navIdentity).click();
    }
    
    public void editUser() {
        Graphene.guardHttp(navUserEdit).click();
    }
    
    public void changePassword() {
        Graphene.guardHttp(navUserChangePassword).click();
    }
    
    public void logout() {
        Graphene.guardHttp(navUserLogout).click();
    }
    
    public String getLoggedInName() {
        return navIdentity.getText().trim();
    }
    
}
