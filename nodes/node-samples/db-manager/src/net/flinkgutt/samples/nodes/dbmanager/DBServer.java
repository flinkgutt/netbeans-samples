/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.dbmanager;

import net.flinkgutt.samples.nodes.api.db.IDatabaseServer;

/**
 *
 * @author Christian
 */
class DBServer implements IDatabaseServer {

    private String displayName, driverUrl, defaultPort, connectionString;
    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDriverUrl() {
        return driverUrl;
    }

    @Override
    public String getDefaultPort() {
        return defaultPort;
    }

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    public DBServer(String displayName, String driverUrl, String defaultPort, String connectionString) {
        this.displayName = displayName;
        this.driverUrl = driverUrl;
        this.defaultPort = defaultPort;
        this.connectionString = connectionString;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDriverUrl(String driverUrl) {
        this.driverUrl = driverUrl;
    }

    public void setDefaultPort(String defaultPort) {
        this.defaultPort = defaultPort;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    
}
