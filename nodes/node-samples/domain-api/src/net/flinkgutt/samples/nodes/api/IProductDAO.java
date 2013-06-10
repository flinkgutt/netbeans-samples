package net.flinkgutt.samples.nodes.api;

import java.util.List;

/**
 *
 * @author Christian
 */
public interface IProductDAO<P extends IProduct, C extends ICategory> {
    
    List<P> getProducts(C category);
    boolean save(P product);
}
