package coop.stlma.tech.protocolsn.pluginlib.registration.error;

/**
 * Indicates a failure in the plugin registration process.
 *
 * @author John Meyerin
 */
public class PluginRegistrationException extends RuntimeException {
    public PluginRegistrationException(String message) {
        super(message);
    }
}
