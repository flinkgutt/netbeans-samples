/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.domain.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.flinkgutt.samples.nodes.api.ICategory;

/**
 *
 * @author Christian
 */
public class Category implements ICategory {

    private Integer categoryID;
    private String name, description;
    private List<ICategory> children = new ArrayList<ICategory>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private ICategory parent;
    private int sortOrder = 0;

    public Category(Integer id, ICategory parent, String categoryName) {
        categoryID = id;
        name = categoryName;
        this.parent = parent;
    }

    public Category() {
    }

    @Override
    public void reorder(int[] perm) {
        ICategory[] reordered = new Category[children.size()];
        for (int i = 0; i < perm.length; i++) {
            int j = perm[i];
            ICategory c = children.get(i);
            c.setSortOrder(j);
            reordered[j] = c;
        }
        
        children = new ArrayList<ICategory>();
        children.addAll(Arrays.asList(reordered));
        pcs.firePropertyChange("children", null, children);
    }
    
    @Override
    public Integer getCategoryID() {
        return categoryID;
    }

    @Override
    public Integer getParentID() {
        if (parent == null) {
            return 0;
        }
        return parent.getCategoryID();
    }

    public void setParent(ICategory parent) {
        this.parent = parent;
    }

    @Override
    public ICategory getParent() {
        return parent;
    }

    public void setCategoryID(Integer id) {
        categoryID = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        pcs.firePropertyChange("name", oldName, this.name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        pcs.firePropertyChange("description", oldDescription, this.description);
    }

    @Override
    public List<ICategory> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<ICategory> categories) {
        children = categories;
    }

    @Override
    public void addChild(ICategory child) {
        children.add(child);
        pcs.firePropertyChange("children", null, children);
    }

    @Override
    public void removeChild(ICategory child) {
        children.remove(child);
        pcs.firePropertyChange("children", child, children);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    
}
