package net.flinkgutt.samples.nodes.domain.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.flinkgutt.samples.nodes.api.db.IConnectionService;
import net.flinkgutt.samples.nodes.api.db.IDatabaseServerSettings;
import net.flinkgutt.samples.nodes.api.db.ConnectionAttemptReturnObject;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Christian
 */
@ServiceProvider(service = IConnectionService.class)
public class ConnectionProvider extends SuperDAO implements IConnectionService {

    public enum DBServer {

        MySQL, PostgreSQL, NONE
    }
    private DBServer selected;

    @Override
    public ConnectionAttemptReturnObject connect(IDatabaseServerSettings settings) {
        final DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(settings.getDriver());
        ds.setUrl(settings.getJDBCString() + settings.getDBHostname() + ":" + settings.getDBPort() + "/" + settings.getDBName());
        ds.setUsername(settings.getDBUsername());
        ds.setPassword(settings.getDBPassword());
        // TODO Figure out a better way to do this thing
        if (settings.getDBIdentifier().equalsIgnoreCase("com.mysql")) {
            selected = DBServer.MySQL;
        } else if (settings.getDBIdentifier().equalsIgnoreCase("org.postgresql")) {
            selected = DBServer.PostgreSQL;
        } else {
            selected = DBServer.NONE;
        }

        setTemplate(new NamedParameterJdbcTemplate(ds));
        try {
            checkForSampleDB();
        } catch (IOException ex) {
            Logger.getLogger(SuperDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ConnectionAttemptReturnObject(false, "Could not install sample data.\n" + ex.getMessage());
        }
        // Check to see if the connection is open, or rather in this case use the naive isClosed().
        // There are ways for a connection to not be formally closed but still "closed".
        // So while this isn't perfect, it's ok for most situations.
        try {
            ds.getConnection().isClosed();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            return new ConnectionAttemptReturnObject(false, ex.getMessage());
        }

        firePropertyChange("connection", "disconnected", "connected");
        setConnected(true);
        return new ConnectionAttemptReturnObject(true, "");
    }

    @Override
    public ConnectionAttemptReturnObject testConnect(IDatabaseServerSettings testSettings) {
        // TODO Check if everything we need is filled out properly
        boolean clearToConnectToDB = true;
        // If this connection is to use an SSH tunnel to get to the DB
        if (testSettings.useTunnel()) {
            // TODO Implement setup of tunnel
            // some check to see if the tunnel is up and running
            // set clearToConnectToDB = false if the tunnel isn't open
            clearToConnectToDB = false;
        }

        // if the attempt to create a tunnel failed or some validation (TODO) fails on username, password, servername, port etc.
        if (!clearToConnectToDB) {
            return new ConnectionAttemptReturnObject(false, "Attempt to create SSH tunnel failed");
        }

        // TODO Add some testing/validation of the parameters to the connection attempt.
        final String dbUrl = testSettings.getJDBCString() + testSettings.getDBHostname() + ":" + testSettings.getDBPort() + "/" + testSettings.getDBName();
        final String username = testSettings.getDBUsername();
        final String password = testSettings.getDBPassword();

        Connection connection = null;
        boolean success = true;
        String errorMessage = "";
        try {
            Class.forName(testSettings.getDriver());
            connection = DriverManager.getConnection(dbUrl,
                    username, password);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            /*
             ** "Connect error"...
             */
            success = false;
            errorMessage = ex.getMessage();
        } catch (java.lang.ClassNotFoundException ex) {
            /*
             ** "Driver error"...
             */
            success = false;
            errorMessage = ex.getMessage();
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        // TODO Close SSH Tunnel
        return new ConnectionAttemptReturnObject(success, errorMessage);
    }

    protected void checkForSampleDB() throws IOException {
        // Run SQL Create script based on what DB we have chosen to use
        switch (selected) {
            case MySQL:
                // Load mysql.sql
                getJdbcTemplate().getJdbcOperations().batchUpdate(removeEmptyLines("mysql.sql"));
                break;
            case PostgreSQL:
                // Load postgresql.sql
                getJdbcTemplate().getJdbcOperations().batchUpdate(removeEmptyLines("postgresql.sql"));
                break;
            default:
                break;
        }
    }

    private String[] removeEmptyLines(final String file) throws FileNotFoundException {
        final InputStream is = getClass().getResourceAsStream(file);
        final String text = new Scanner(is, "UTF-8").useDelimiter("\\A").next();

        final String[] tmp = text.split(";");
        // jdbctemplate.batchUpdate() does _NOT_ like empty sql queries, so we need to remove every empty line we can find.
        // Yes, this is ugly. I know.
        final List<String> arr = new ArrayList<String>();
        for (final String string : tmp) {
            if (!string.trim().isEmpty()) {
                arr.add(string);
            }
        }
        return arr.toArray(new String[arr.size()]);
    }
}
