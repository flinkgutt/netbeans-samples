
package net.flinkgutt.samples.nodes.api;

import java.util.List;


/**
 *
 * @author Christian
 */

public interface ICategoryDAO<T extends ICategory> {
    List<T> getCategoriesWithParent(T parent);

    T getRootCategory();

    T createCategory(T parent, String name);
    
    void deleteCategory(T category);
    boolean hasChildren(T category);
    
    void update(T category);

    boolean addCategory(T category);
}
