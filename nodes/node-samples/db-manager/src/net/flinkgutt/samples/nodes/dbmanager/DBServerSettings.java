/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.dbmanager;

import net.flinkgutt.samples.nodes.api.db.IDatabaseServerSettings;

/**
 *
 * @author Christian
 */
public class DBServerSettings implements IDatabaseServerSettings {

    private String displayName, dbHostname, dbUsername, dbPassword, dbName, dbIdentifier;
    private String sshHostname, sshUsername, sshPassword;
    private String driver, jdbcString;
    private int sshPort, dbPort;
    private boolean useTunnel;
    // The identifier used for this server settings in whatever storage mechanism is used
    private String storageID; 

    @Override
    public boolean useTunnel() {
        return useTunnel;
    }

    public void setUseTunnel(boolean useTunnel) {
        this.useTunnel = useTunnel;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDBHostname(String dbHostName) {
        this.dbHostname = dbHostName;
    }

    @Override
    public String getDBUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    @Override
    public String getDBPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    @Override
    public String getDBName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public int getDBPort() {
        return dbPort;
    }

    public void setDBPort(int dbPort) {
        this.dbPort = dbPort;
    }

    @Override
    public String getSSHHostname() {
        return sshHostname;
    }

    public void setSSHHostname(String sshHostname) {
        this.sshHostname = sshHostname;
    }

    @Override
    public String getSSHUsername() {
        return sshUsername;
    }

    public void setSSHUsername(String sshUsername) {
        this.sshUsername = sshUsername;
    }

    @Override
    public int getSSHPort() {
        return sshPort;
    }

    public void setSSHPort(int sshPort) {
        this.sshPort = sshPort;
    }

    @Override
    public String getDBHostname() {
        return dbHostname;
    }

    @Override
    public String getSSHPassword() {
        return sshPassword;
    }
    
    @Override
    public String toString() {
        return displayName;
    }

    @Override
    public String getDBIdentifier() {
        return dbIdentifier;
    }

    public void setDBIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String getJDBCString() {
        return jdbcString;
    }

    public void setJDBCString(String jdbcString) {
        this.jdbcString = jdbcString;
    }

    void setSSHPassword(String password) {
        this.sshPassword = password;
    }

    @Override
    public String getStoredID() {
        return storageID;
    }
    
    public void setStorageID(String storageIdentifier) {
        storageID = storageIdentifier;
    }
    
    
}
