package cz.muni.fi.pv243.et.security;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.Logger;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Cause;
import org.jboss.solder.messages.Message;

@MessageLogger
public interface EventLog {

    @Log @Message("Access denied.")
    void logAccessDenied(@Cause Throwable t);

    @Log @Message("Authentication failed.")
    void logAuthenticationFailed(@Cause Throwable t);

    @Log(level = Logger.Level.DEBUG) @Message("Person created: ID=%d")
    void logPersonCreated(Long personId);

    @Log() @Message("Initializing users.")
    void logInitializingUsers();

    @Log() @Message("General error")
    void logGeneralError(@Cause Throwable t);
}
