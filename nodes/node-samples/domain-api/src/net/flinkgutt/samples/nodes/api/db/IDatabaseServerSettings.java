/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.api.db;

/**
 *
 * @author Christian
 */
public interface IDatabaseServerSettings {
    String getDisplayName();
    String getDriver();
    String getJDBCString();
    String getDBHostname();
    String getDBUsername();
    String getDBPassword();
    String getDBName();
    int getDBPort();
    int getRemoteDbPort();
    
    boolean useTunnel();
    String getSSHHostname();
    String getSSHUsername();
    String getSSHPassword();
    int getSSHPort();

    String getDBIdentifier();

    /**
     * Every DatabaseServerSetting needs to be stored with some unique identifier
     * @return the identifier this server setting is stored with
     */
    String getStoredID();

}
