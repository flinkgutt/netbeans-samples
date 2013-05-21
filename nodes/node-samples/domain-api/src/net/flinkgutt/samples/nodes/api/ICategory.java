
package net.flinkgutt.samples.nodes.api;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author Christian
 */
public interface ICategory {
    final int CATEGORY_NAME_CHANGE = 1;
    final int CATEGORY_CHILD_ADD = 2;
    final int CATEGORY_CHILD_REMOVE = 3;
    
    Integer getCategoryID();
    Integer getParentID();
    String getName();
    void setName(String name);
    
    ICategory getParent();
    //void setParent(ICategory parent);
    
    List<ICategory> getChildren();
    void addChild(ICategory child);
    void removeChild(ICategory child);
    
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
}
