package cz.muni.fi.pv243.et.ftest.objects;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.WebElement;

public class LoginForm {
    
    @FindBy(id="btn-login")
    WebElement loginBtn;
    
    @FindBy(id="btn-signup")
    WebElement signUpBtn;
    
    @FindBy(id="username")
    WebElement usernameField;
    
    @FindBy(id="password")
    WebElement passwordField;
    
    public void login(String user, String pwd) {
        usernameField.sendKeys(user);
        passwordField.sendKeys(pwd);
        Graphene.guardHttp(loginBtn).click();
    }
    
}
