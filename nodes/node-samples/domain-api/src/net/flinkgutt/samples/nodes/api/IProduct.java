package net.flinkgutt.samples.nodes.api;

/**
 *
 * @author Christian
 */
public interface IProduct {
    String getName();

    String getDescription();
    
    int getProductID();

    void setName(String name);
    
    void setDescription(String description);

    int getSortOrder();

    boolean isActive();

    void setActive(boolean active);

    void setSortOrder(int sortOrder);
}
