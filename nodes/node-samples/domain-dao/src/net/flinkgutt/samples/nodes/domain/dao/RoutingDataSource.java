package net.flinkgutt.samples.nodes.domain.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.sql.DataSource;
import org.openide.util.NbPreferences;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author Christian
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static String dataSourceId;

    private Map<String, DataSource> targetDataSources = new HashMap<String, DataSource>();

    @Override
    public void setTargetDataSources(Map targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    private final String CONFIGURATION_NODE_NAME = "netbeans-samples_servers";

    @Override
    protected DataSource determineTargetDataSource() {

        // This precludes us from adjusting the DataSource properties without restarting. 
        // Thus this is a sub-optimal solution for certain applications.
        DataSource dataSource = targetDataSources.get(determineCurrentLookupKey());
        if (dataSource != null) {
            System.out.println("Just returning the dataSource from the map.");
            return dataSource;
        }
        String lookupKey = determineCurrentLookupKey();
        Preferences server = NbPreferences.root().node(CONFIGURATION_NODE_NAME).node(lookupKey);
        
        if (server != null) {
        
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName(server.get("dbdriver", ""));
            String url = server.get("jdbcurl", "") + server.get("dbhostname", "") + ":" + server.getInt("dbport", 0) + "/" + server.get("dbname", "");
            ds.setUrl(url);
            ds.setUsername(server.get("dbusername", ""));
            ds.setPassword(server.get("dbpassword", ""));
            // Store the DataSource in the map, so we don't have to lookup the NbPreferences each time.
            targetDataSources.put(lookupKey, ds);
            return ds;
        }

        // SSH specific settings
        // TODO Look into how the SSH should be routed...
        /*
        
         se.setUseTunnel(server.getBoolean("useTunnel", false));
         se.setSSHHostname(server.get("sshHostname", ""));
         se.setSSHUsername(server.get("sshUsername", ""));
         se.setSSHPort(server.getInt("sshPort", 22));

         se.setRemoteDbPort(server.getInt("remoteDBPort", 0));
        
         */
        throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
    }

    @Override
    protected String determineCurrentLookupKey() {
        return dataSourceId; /*a lookup key that can pick the actual datasource from the Map*/

    }

    @Override
    public void afterPropertiesSet() {
        // do nothing
        // overridden to avoid datasource validation error by Spring
    }

    public static void setDataSourceId(String id) {
        dataSourceId = id;
    }
}
