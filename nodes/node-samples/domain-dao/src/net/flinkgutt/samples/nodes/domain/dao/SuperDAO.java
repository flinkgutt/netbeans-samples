package net.flinkgutt.samples.nodes.domain.dao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author Christian
 */
// TODO Look into utilizing the NamedParameterJdbcDaoSupport class.
// TODO Clean up the code
public abstract class SuperDAO {

    private static NamedParameterJdbcTemplate jdbcTemplate;
   
    private static boolean isConnected = false;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public SuperDAO() {
    }
    public static NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    protected static void setTemplate(NamedParameterJdbcTemplate template) {
        SuperDAO.jdbcTemplate = template;
    }
    protected static void setConnected(boolean status) {
        isConnected = Boolean.valueOf(status);
    }

    //@Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }
    //@Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(propertyName, pcl);
    }
    
    //@Override
    public void removePropertyChangelistener(PropertyChangeListener plc) {
        pcs.removePropertyChangeListener(plc);
    }
    
    //@Override
    public void removePropertyChangelistener(String propertyName, PropertyChangeListener plc) {
        pcs.removePropertyChangeListener(propertyName, plc);
    }
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }
    //@Override
    public boolean isConnected() {
        return isConnected;
    }
}
