package net.flinkgutt.samples.nodes.view.reorder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import net.flinkgutt.samples.nodes.api.ICategory;
import net.flinkgutt.samples.nodes.api.ICategoryDAO;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Christian
 */
class CategoryChildFactory extends ChildFactory<ICategory> implements PropertyChangeListener {

    private ICategory currentCategory;
    private ICategoryDAO categoryDAO = Lookup.getDefault().lookup(ICategoryDAO.class);

    public CategoryChildFactory(ICategory category) {
        currentCategory = category;
        currentCategory.addPropertyChangeListener(WeakListeners.propertyChange(this, currentCategory));
    }

    @Override
    protected Node createNodeForKey(ICategory key) {
        return new CategoryNode(key, new InstanceContent());
    }

    @Override
    protected boolean createKeys(List<ICategory> toPopulate) {
        if (currentCategory == null || categoryDAO == null) {
            return true;
        }
        if (toPopulate.isEmpty()) {
            List<ICategory> categories = categoryDAO.getCategoriesWithParent(currentCategory);
            toPopulate.addAll(categories);
        } else {
            // We don't need to do anything because the list is allready populated, see propertyChange
        }
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // If we add or remove a child category we need to call refresh to update the view
        if (evt.getPropertyName().equals("children")) {
            currentCategory.setChildren((List<ICategory>) evt.getNewValue());
            // We update the db
            categoryDAO.updateChildrenSortOrder(currentCategory);
            createKeys(currentCategory.getChildren());
            this.refresh(false);
        }


    }
}
