package net.flinkgutt.samples.nodes.api.db;

/**
 * Class used to signal if an attempt at connecting to a database was successful or not.
 * @author Christian
 */
public class ConnectionAttemptReturnObject {
    private final boolean success;
    private final String message;

    public ConnectionAttemptReturnObject(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccessful() {
        return success;
    }

    public String getErrorMessage() {
        return message;
    }
    
}
