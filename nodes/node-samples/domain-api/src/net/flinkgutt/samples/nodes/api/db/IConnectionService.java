
package net.flinkgutt.samples.nodes.api.db;

import java.beans.PropertyChangeListener;

/**
 *
 * @author Christian
 */
public interface IConnectionService {
    ConnectionAttemptReturnObject connect(IDatabaseServerSettings settings);

    void addPropertyChangeListener(PropertyChangeListener pcl);
    void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl);

    void removePropertyChangelistener(PropertyChangeListener plc);
    void removePropertyChangelistener(String propertyName, PropertyChangeListener plc);

    boolean isConnected();

    ConnectionAttemptReturnObject testConnect(IDatabaseServerSettings testSettings);
}
