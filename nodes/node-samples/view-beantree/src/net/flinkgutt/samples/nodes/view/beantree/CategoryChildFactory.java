
package net.flinkgutt.samples.nodes.view.beantree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import net.flinkgutt.samples.nodes.api.ICategory;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.WeakListeners;

/**
 *
 * @author Christian
 */
class CategoryChildFactory extends ChildFactory<ICategory> implements PropertyChangeListener {

    private ICategory currentCategory;

    public CategoryChildFactory(ICategory category) {
        currentCategory = category;
        currentCategory.addPropertyChangeListener(WeakListeners.propertyChange(this, currentCategory)); // TODO Implement PropertyChangeListener
    }

    @Override
    protected Node createNodeForKey(ICategory key) {
        return new CategoryNode(key);
    }

    @Override
    protected boolean createKeys(List<ICategory> toPopulate) {
        if (currentCategory == null) {
            return true;
        }
        toPopulate.addAll(currentCategory.getChildren());
        return true;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // If we add or remove a child category we need to call refresh to update the view
        if (evt.getPropertyName().equals("children")) {
            this.refresh(true);

        }


    }
}
