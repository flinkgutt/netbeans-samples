package net.flinkgutt.samples.nodes.dbmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import net.flinkgutt.samples.nodes.api.db.IDatabaseServerSettings;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

/**
 *
 * @author Christian
 */
public class ServerStoreService {

    public static final String CONFIGURATION_NODE_NAME = "netbeans-samples_servers";

    public static List<IDatabaseServerSettings> getConfiguredServers() throws BackingStoreException {
        List<IDatabaseServerSettings> serverList = new ArrayList<IDatabaseServerSettings>();

        Preferences servers = NbPreferences.root().node(CONFIGURATION_NODE_NAME);
        if (servers != null) {
            String[] dbservers = servers.childrenNames();
            for (String specificServer : dbservers) {
                if (specificServer.startsWith("DBSERVER-")) {
                    Preferences s = servers.node(specificServer);
                    DBServerSettings se = new DBServerSettings();
                    // DB and Connection settings
                    se.setDisplayName(s.get("displayname", ""));
                    se.setDBHostname(s.get("dbhostname", ""));
                    se.setDBPort(s.getInt("dbport", 0));
                    se.setDbName(s.get("dbname", ""));
                    se.setDbUsername(s.get("dbusername", ""));
                    se.setDbPassword(s.get("dbpassword", ""));
                    // DB and Storage settings 
                    se.setStorageID(specificServer);
                    se.setDBIdentifier(s.get("dbidentifier", ""));
                    se.setDriver(s.get("dbdriver", ""));
                    se.setJDBCString(s.get("jdbcurl", ""));
                    // SSH specific settings
                    se.setUseTunnel(s.getBoolean("useTunnel", false));
                    se.setSSHHostname(s.get("sshHostname", ""));
                    se.setSSHUsername(s.get("sshUsername", ""));
                    se.setSSHPort(s.getInt("sshPort", 22));

                    se.setRemoteDbPort(s.getInt("remoteDBPort", 0));

                    serverList.add(se);
                }
            }
        }
        return serverList;
    }

    public static void storeServer(IDatabaseServerSettings s) {
        // Just so we have something to test with for the time being
        Preferences servers = NbPreferences.root().node(CONFIGURATION_NODE_NAME);

        String storedId = s.getStoredID();
        System.out.println("Storing server: '" + storedId + "'");
        Preferences settings = servers.node(storedId);
        settings.put("displayname", s.getDisplayName());
        settings.put("dbhostname", s.getDBHostname());
        settings.put("dbusername", s.getDBUsername());
        settings.put("dbpassword", s.getDBPassword());
        settings.put("dbname", s.getDBName());
        settings.putInt("dbport", s.getDBPort());

        settings.putBoolean("useTunnel", s.useTunnel());
        settings.put("sshHostname", s.getSSHHostname());
        settings.put("sshUsername", s.getSSHUsername());
        settings.put("sshPassword", s.getSSHPassword());
        settings.putInt("sshPort", s.getSSHPort());

        settings.putInt("remoteDBPort", s.getRemoteDbPort());

        settings.put("dbidentifier", s.getDBIdentifier());
        settings.put("jdbcurl", s.getJDBCString());
        settings.put("dbdriver", s.getDriver());
        try {
            servers.flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static void removeServer(String storedID) {
        Preferences configNode = NbPreferences.root().node(CONFIGURATION_NODE_NAME).node(storedID);
        try {
            configNode.removeNode();
            configNode.flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static IDatabaseServerSettings getServer(String serverID) {
        Preferences s = NbPreferences.root().node(CONFIGURATION_NODE_NAME).node(serverID);

        DBServerSettings se = new DBServerSettings();
        // DB and Connection settings
        se.setDisplayName(s.get("displayname", ""));
        se.setDBHostname(s.get("dbhostname", ""));
        se.setDBPort(s.getInt("dbport", 0));
        se.setDbName(s.get("dbname", ""));
        se.setDbUsername(s.get("dbusername", ""));
        se.setDbPassword(s.get("dbpassword", ""));
        // DB and Storage settings 
        se.setStorageID(serverID);
        se.setDBIdentifier(s.get("dbidentifier", ""));
        se.setDriver(s.get("dbdriver", ""));
        se.setJDBCString(s.get("jdbcurl", ""));
        // SSH specific settings
        se.setUseTunnel(s.getBoolean("useTunnel", false));
        se.setSSHHostname(s.get("sshHostname", ""));
        se.setSSHUsername(s.get("sshUsername", ""));
        se.setSSHPort(s.getInt("sshPort", 22));

        se.setRemoteDbPort(s.getInt("remoteDBPort", 0));

        return se;

    }

}
