
package net.flinkgutt.samples.nodes.api;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author Christian
 */
public interface ICategory {
    
    Integer getCategoryID();
    Integer getParentID();
    String getName();
    void setName(String name);
    
    ICategory getParent();
    
    List<ICategory> getChildren();
    void addChild(ICategory child);
    void removeChild(ICategory child);
    
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);

    String getDescription();

    void setDescription(String description);

    void setChildren(List<ICategory> categories);

    int getSortOrder();

    void setSortOrder(int sortOrder);

    void reorder(int[] perm);
}
