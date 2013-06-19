package cz.muni.fi.pv243.et.message;

import org.apache.deltaspike.core.api.message.annotation.MessageBundle;
import org.apache.deltaspike.core.api.message.annotation.MessageContextConfig;
import org.apache.deltaspike.core.api.message.annotation.MessageTemplate;

@MessageBundle
@MessageContextConfig(messageSource = {"messages"})
public interface SecurityMessage {

    @MessageTemplate("{security.roleMissing}")
    String roleMissing();

    @MessageTemplate("{security.notAuthenticated}")
    String notAuthenticated();
}
