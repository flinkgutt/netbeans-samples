
package net.flinkgutt.samples.nodes.api;

import java.util.List;


/**
 *
 * @author Christian
 */

public interface ICategoryDAO<T extends ICategory> {
    List<T> getCategoriesWithParent(Integer parentID);

    T getRootCategory();

    void updateSomeCategories();

    T createCategory(Integer parent, String name);
    
}
