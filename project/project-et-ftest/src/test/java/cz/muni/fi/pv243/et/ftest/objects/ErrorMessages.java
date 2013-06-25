package cz.muni.fi.pv243.et.ftest.objects;

import java.util.ArrayList;
import java.util.List;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.WebElement;

public class ErrorMessages {
        
    @FindBy(jquery = "ul#error-messages > li")
    List<WebElement> messages;
    
    public List<String> getMessages() {
        List<String> stringMessages = new ArrayList<String>();
        for(WebElement elem : messages) {
            stringMessages.add(elem.getText());
        }
        return stringMessages;
    }
    
    public boolean contains(String message) {
        return getMessages().contains(message);
    }
    
}
