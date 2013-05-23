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
    
    boolean useTunnel();
    String getSSHHostname();
    String getSSHUsername();
    String getSSHPassword();
    int getSSHPort();

    String getDBIdentifier();
}
