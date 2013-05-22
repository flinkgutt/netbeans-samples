/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.api.db;

/**
 *
 * @author Christian
 */
public interface IDatabaseServer {
    String getDisplayName();
    String getDriverUrl();
    String getDefaultPort();
    String getConnectionString();
}
