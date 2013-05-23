package net.flinkgutt.samples.nodes.domain.dao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.flinkgutt.samples.nodes.api.IConnectionEvent;
import net.flinkgutt.samples.nodes.api.db.IConnectionService;
import net.flinkgutt.samples.nodes.api.db.IDatabaseServerSettings;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Christian
 */
public abstract class SuperDAO implements Lookup.Provider, IConnectionService {

    NamedParameterJdbcTemplate jdbcTemplate;
    private DriverManagerDataSource dataSource;

    public enum DBServer {

        MySQL, PostgreSQL
    }
    public DBServer selected = DBServer.MySQL;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    Lookup.Result<IConnectionEvent> result = null;
    Lookup.Result<IDatabaseServerSettings> settingsResult = null;

    public SuperDAO() {
        //result = Utilities.actionsGlobalContext().lookupResult(IConnectionEvent.class);
        //result.addLookupListener(this);
    }

    @Override
    public void connect(IDatabaseServerSettings settings) {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(settings.getDriver());
        dataSource.setUrl(settings.getJDBCString() + settings.getDBHostname() + ":" + settings.getDBPort() + "/" + settings.getDBName());
        dataSource.setUsername(settings.getDBUsername());
        dataSource.setPassword(settings.getDBPassword());
        
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        try {
            checkForSampleDB();
        } catch (IOException ex) {
            Logger.getLogger(SuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO check here to see if the connection is indeed intact
        pcs.firePropertyChange("connection", "disconnected", "connected");
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

    /**
     * @Override public void resultChanged(LookupEvent ev) {
     *
     * System.out.println("LookupEvent: " + ev.getSource()); Lookup.Result r =
     * (Lookup.Result) ev.getSource();
     *
     * Collection instances = r.allInstances(); for (Iterator it =
     * instances.iterator(); it.hasNext();) { Object object = it.next();
     * System.out.println("\tInstance of " + object.getClass()); }
     *
     * Collection<IConnectionEvent> events = r.allInstances();
     * System.out.println("events.size() => " + events.size()); if
     * (events.size() == 1) { IConnectionEvent event = events.iterator().next();
     * if (event.getEvent() == IConnectionEvent.CONNECT) { //connect(); } else
     * if (event.getEvent() == IConnectionEvent.DISCONNECT) { //disconnect(); //
     * TODO Implement a disconnect, or not. } } // if correct event, call
     * connect() }
     */
    private String[] removeEmptyLines(String file) throws FileNotFoundException {
        String text = "";
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

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangelistener(PropertyChangeListener plc) {
        pcs.removePropertyChangeListener(plc);
    }
    InstanceContent ic = new InstanceContent();
    AbstractLookup lookup = new AbstractLookup(ic);

    @Override
    public Lookup getLookup() {
        return lookup;
    }
}
