package net.flinkgutt.samples.nodes.api;

/**
 *
 * @author Christian
 */
public interface IProduct {
    String getName();

    int getProductID();

    void setName(String name);

    int getSortOrder();

    boolean isActive();

    void setActive(boolean active);

    void setSortOrder(int sortOrder);
}
