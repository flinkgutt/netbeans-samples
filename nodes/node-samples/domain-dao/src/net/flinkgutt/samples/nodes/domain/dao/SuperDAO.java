package net.flinkgutt.samples.nodes.domain.dao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import org.openide.util.Lookup;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Christian
 */
// TODO Look into utilizing the NamedParameterJdbcDaoSupport class.
// TODO Clean up the code
public abstract class SuperDAO implements IConnectionService {

    static NamedParameterJdbcTemplate jdbcTemplate;
    private DriverManagerDataSource dataSource;
    boolean isConnected = false;
    public enum DBServer {

        MySQL, PostgreSQL
    }
    public DBServer selected;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    Lookup.Result<IDatabaseServerSettings> settingsResult;
    
    public SuperDAO() {
    }
    
    @Override
    public boolean connect(IDatabaseServerSettings settings) {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(settings.getDriver());
        dataSource.setUrl(settings.getJDBCString() + settings.getDBHostname() + ":" + settings.getDBPort() + "/" + settings.getDBName());
        dataSource.setUsername(settings.getDBUsername());
        dataSource.setPassword(settings.getDBPassword());
        // TODO Figure out a better way to do this thing
        if (settings.getDBIdentifier().equalsIgnoreCase("com.mysql")) {
            selected = DBServer.MySQL;
        } else if (settings.getDBIdentifier().equalsIgnoreCase("org.postgresql")) {
            selected = DBServer.PostgreSQL;
        }

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
            dataSource.getConnection().isClosed();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            isUp = false;
        }
        
        pcs.firePropertyChange("connection", "disconnected", "connected");
        isConnected = true;
        return isUp;
    }

    private void checkForSampleDB() throws IOException {
        // Run SQL Create script based on what DB we have chosen to use
        switch (selected) {
            case MySQL:
                // Load mysql.sql
                jdbcTemplate.getJdbcOperations().batchUpdate(removeEmptyLines("mysql.sql"));
                break;
            case PostgreSQL:
                // Load postgresql.sql
                jdbcTemplate.getJdbcOperations().batchUpdate(removeEmptyLines("postgresql.sql"));
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

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(propertyName, pcl);
    }
    
    @Override
    public void removePropertyChangelistener(PropertyChangeListener plc) {
        pcs.removePropertyChangeListener(plc);
    }
    
    @Override
    public void removePropertyChangelistener(String propertyName, PropertyChangeListener plc) {
        pcs.removePropertyChangeListener(propertyName, plc);
    }
    
    @Override
    public boolean isConnected() {
        return isConnected;
    }
}
