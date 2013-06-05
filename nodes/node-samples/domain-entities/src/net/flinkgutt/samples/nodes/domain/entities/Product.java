package net.flinkgutt.samples.nodes.domain.entities;

import net.flinkgutt.samples.nodes.api.IProduct;

/**
 *
 * @author Christian
 */
public class Product implements IProduct {
    
    private int productID;
    private String name;
    private boolean active;
    private int sortOrder;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
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
