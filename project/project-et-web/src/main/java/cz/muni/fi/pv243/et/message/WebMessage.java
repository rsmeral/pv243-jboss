package cz.muni.fi.pv243.et.message;

import org.apache.deltaspike.core.api.message.annotation.MessageBundle;
import org.apache.deltaspike.core.api.message.annotation.MessageContextConfig;
import org.apache.deltaspike.core.api.message.annotation.MessageTemplate;

@MessageBundle
@MessageContextConfig(messageSource = {"messages"})
public interface WebMessage {

    @MessageTemplate("{error.loginFailed}")
    String loginFailed();

    @MessageTemplate("{error.userIdInUse}")
    String userIdInUse();

    @MessageTemplate("{error.userEmailInUse}")
    String userEmailInUse();

    @MessageTemplate("{error.passwordMismatch}")
    String passwordMismatch();

    @MessageTemplate("{exception.accessDenied}")
    String accessDenied(String reasons);

    @MessageTemplate("{exception.authenticationFailed}")
    String authenticationFailed();

    @MessageTemplate("{exception.generalError}")
    String generalError();
}
