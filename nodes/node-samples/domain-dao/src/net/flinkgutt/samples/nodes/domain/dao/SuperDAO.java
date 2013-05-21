package net.flinkgutt.samples.nodes.domain.dao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Christian
 */
public abstract class SuperDAO {

    public NamedParameterJdbcTemplate jdbcTemplate;

    public enum DBServer {

        MySQL, PostgreSQL
    }
    public DBServer selected = DBServer.PostgreSQL;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public SuperDAO() {
        if (jdbcTemplate == null) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

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
        }
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
        String text = "";
        try {
            text = new Scanner(new File(getClass().getResource(file).toURI()), "UTF-8").useDelimiter("\\A").next();
        } catch (URISyntaxException ex) {
            Logger.getLogger(SuperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
