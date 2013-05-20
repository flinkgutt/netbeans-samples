
package net.flinkgutt.samples.nodes.domain.dao;

import java.util.ArrayList;
import java.util.List;

import net.flinkgutt.samples.nodes.api.ICategoryDAO;
import net.flinkgutt.samples.nodes.domain.entities.Category;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Christian
 */
@ServiceProvider(service = ICategoryDAO.class)
public class CategoryDAO implements ICategoryDAO<Category> {

    private int teller = 1;
    private static List<Category> categories = new ArrayList<Category>();
    private Category root;

    public CategoryDAO() {
        // Just so we have something to play around with
        addDummyData();
    }

    private void addDummyData() {
        root = new Category(0, 0, "ROOT");
        Category c1 = new Category(teller++, 0, "Category #1");
        Category c2 = new Category(teller++, 0, "Category #2");
        Category c3 = new Category(teller++, 0, "Category #3");
        Category c4 = new Category(teller++, 0, "Category #4");

        Category c1_1 = new Category(teller++, 1, "Category #1-1");
        Category c1_2 = new Category(teller++, 1, "Category #1-2");
        Category c1_3 = new Category(teller++, 1, "Category #1-3");
        Category c2_1 = new Category(teller++, 2, "Category #2-1");
        Category c2_2 = new Category(teller++, 2, "Category #2-2");

        c1.addChild(c1_1);
        c1.addChild(c1_2);
        c1.addChild(c1_3);

        c2.addChild(c2_1);
        c2.addChild(c2_2);

        root.addChild(c1);
        root.addChild(c2);
        root.addChild(c3);
        root.addChild(c4);
        categories.add(root);
    }

    @Override
    public List<Category> getCategoriesWithParent(Integer parentID) {
        return categories;
    }
    
    @Override
    public void updateSomeCategories() {
        // TODO remove
    }
    @Override
    public Category createCategory(Integer parent, String name) {
        return new Category(teller++, parent, name);
    }
    @Override
    public Category getRootCategory() {
        if (root == null) {
            addDummyData();
        }
        return root;
    }
}
