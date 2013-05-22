package net.flinkgutt.samples.nodes.domain.dao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.flinkgutt.samples.nodes.api.IConnectionEvent;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Christian
 */
public abstract class SuperDAO implements LookupListener, Lookup.Provider {

    public NamedParameterJdbcTemplate jdbcTemplate;
    private DriverManagerDataSource dataSource;

    public enum DBServer {

        MySQL, PostgreSQL
    }
    public DBServer selected = DBServer.MySQL;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    Lookup.Result<IConnectionEvent> result = null;

    public SuperDAO() {
        result = Utilities.actionsGlobalContext().lookupResult(IConnectionEvent.class);
        result.addLookupListener(this);
    }

    private void connect() {
        dataSource = new DriverManagerDataSource();
        if (selected == DBServer.PostgreSQL) {
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://localhost:5432/netbeans-samples");
            dataSource.setUsername("netbeans-samples");
            dataSource.setPassword("secretpassword123");
        } else if (selected == DBServer.MySQL) {
            dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/netbeans-samples");
            dataSource.setUsername("netbeans-samples");
            dataSource.setPassword("secretpassword123");
        }
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

    @Override
    public void resultChanged(LookupEvent ev) {
        System.out.println("LookupEvent: " + ev.getSource());
        Lookup.Result r = (Lookup.Result) ev.getSource();
        Collection<IConnectionEvent> events = r.allInstances();
        System.out.println("events.size() => " + events.size());
        if (events.size() == 1) {
            IConnectionEvent event = events.iterator().next();
            if (event.getEvent() == IConnectionEvent.CONNECT) {
                connect();
            } else if (event.getEvent() == IConnectionEvent.DISCONNECT) {
                //disconnect(); // TODO Implement a disconnect, or not.
            }
        }
        // if correct event, call connect()
    }

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
