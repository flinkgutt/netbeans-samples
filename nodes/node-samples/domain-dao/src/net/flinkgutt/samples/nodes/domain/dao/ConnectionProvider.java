package net.flinkgutt.samples.nodes.domain.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.flinkgutt.samples.nodes.api.db.IConnectionService;
import net.flinkgutt.samples.nodes.api.db.IDatabaseServerSettings;
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

        MySQL, PostgreSQL
    }
    private DBServer selected;
    @Override
    public boolean connect(IDatabaseServerSettings settings) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(settings.getDriver());
        ds.setUrl(settings.getJDBCString() + settings.getDBHostname() + ":" + settings.getDBPort() + "/" + settings.getDBName());
        ds.setUsername(settings.getDBUsername());
        ds.setPassword(settings.getDBPassword());
        // TODO Figure out a better way to do this thing
        if (settings.getDBIdentifier().equalsIgnoreCase("com.mysql")) {
            selected = DBServer.MySQL;
        } else if (settings.getDBIdentifier().equalsIgnoreCase("org.postgresql")) {
            selected = DBServer.PostgreSQL;
        }

        setTemplate(  new NamedParameterJdbcTemplate(ds) );
        try {
            checkForSampleDB();
        } catch (IOException ex) {
            Logger.getLogger(SuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Check to see if the connection is open, or rather in this case use the naive isClosed().
        // There are ways for a connection to not be formally closed but still "closed".
        // So while this isn't perfect, it's ok for most situations.
        boolean isUp = true;
        try {
            ds.getConnection().isClosed();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            return false;
        }

        firePropertyChange("connection", "disconnected", "connected");
        setConnected(isUp);
        return isUp;
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
        }
    }

    private String[] removeEmptyLines(String file) throws FileNotFoundException {
        String text;
        InputStream is = getClass().getResourceAsStream(file);
        text = new Scanner(is, "UTF-8").useDelimiter("\\A").next();

        String[] tmp = text.split(";");
        // jdbctemplate.batchUpdate() does _NOT_ like empty sql queries, so we need to remove every empty line we can find.
        // Yes, this is ugly. I know.
        List<String> arr = new ArrayList<String>();
        for (int i = 0; i < tmp.length; i++) {
            String string = tmp[i];
            if (!string.trim().isEmpty()) {
                arr.add(string);
            }
        }
        return arr.toArray(new String[arr.size()]);
    }
    
}
