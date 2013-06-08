package cz.muni.fi.pv243.et.exception;

import org.apache.deltaspike.core.api.exception.control.annotation.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.annotation.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.core.api.exclude.annotation.Exclude;
import org.jboss.solder.logging.Logger;

import javax.inject.Inject;

@Exclude
@ExceptionHandler
public class DefaultExceptionHandler {

    @Inject
    private Logger logger;

    public void onException(@Handles ExceptionEvent<Throwable> event) {
        logger.error("Handling excepion: " + event.getException().getLocalizedMessage(), event.getException());
        event.handled();
    }
}
