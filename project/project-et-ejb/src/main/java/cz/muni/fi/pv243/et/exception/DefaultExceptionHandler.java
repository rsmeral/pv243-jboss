package cz.muni.fi.pv243.et.exception;


import org.apache.deltaspike.core.api.exception.control.annotation.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.annotation.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.slf4j.Logger;

import javax.inject.Inject;

@ExceptionHandler
public class DefaultExceptionHandler {

    @Inject
    private Logger logger;

    void handleException(@Handles ExceptionEvent<Throwable> exceptionEvent) {
        Throwable t = exceptionEvent.getException();
        logger.error(t.getLocalizedMessage(), t);

        exceptionEvent.handled();
    }
}
