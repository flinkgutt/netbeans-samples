
package net.flinkgutt.samples.nodes.api.db;

import java.beans.PropertyChangeListener;

/**
 *
 * @author Christian
 */
public interface IConnectionService {
    boolean connect(IDatabaseServerSettings settings);

    void addPropertyChangeListener(PropertyChangeListener pcl);
    void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl);

    void removePropertyChangelistener(PropertyChangeListener plc);
    void removePropertyChangelistener(String propertyName, PropertyChangeListener plc);

    boolean isConnected();

    TestConnectReturnObject testConnect(IDatabaseServerSettings testSettings);
}
