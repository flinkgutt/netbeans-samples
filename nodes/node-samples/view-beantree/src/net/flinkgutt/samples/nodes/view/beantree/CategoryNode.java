package net.flinkgutt.samples.nodes.view.beantree;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.flinkgutt.samples.nodes.api.ICategory;
import net.flinkgutt.samples.nodes.api.ICategoryDAO;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.WeakListeners;

/**
 *
 * @author Christian
 */
public class CategoryNode extends AbstractNode implements PropertyChangeListener {

    private ICategoryDAO categoryDAO = Lookup.getDefault().lookup(ICategoryDAO.class);
    private ICategory category;

    public CategoryNode(Children children) {
        super(children);
    }

    public CategoryNode(ICategory category) {
        super(Children.create(new CategoryChildFactory(category), true), Lookup.EMPTY);
        this.category = category;
        this.category.addPropertyChangeListener(WeakListeners.propertyChange(this, this.category));
    }

    @Override
    public boolean canRename() {
        return true;
    }

    @Override
    public void setName(String newDisplayName) {
        String oldDisplayName = category.getName();
        category.setName(newDisplayName);
        fireNameChange(oldDisplayName, newDisplayName);
    }

    @Override
    public String getDisplayName() {
        if (this.category == null) {
            return "";
        }
        return this.category.getName();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            fireDisplayNameChange(evt.getOldValue().toString(), evt.getNewValue().toString());
        }
    }

    @Override
    public Action[] getActions(boolean popup) {
        if (category != null) {
            return new Action[]{
                new CreateCategoryAction()
            };
        } else {
            return new Action[]{};
        }
    }

    private class CreateCategoryAction extends AbstractAction {

        public CreateCategoryAction() {
            putValue(NAME, "Create category");
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String fieldLabel = "Name: ";
            String title = "Enter the name of the new Category";
            NotifyDescriptor.InputLine input = new NotifyDescriptor.InputLine(fieldLabel, title);
            input.setInputText(""); // specify a default name
            Object result = DialogDisplayer.getDefault().notify(input);
            if (result != NotifyDescriptor.OK_OPTION) {
                return;
            }
            String categoryName = input.getInputText();
            ICategory newCat = categoryDAO.createCategory(category.getParentID(), categoryName);
            category.addChild(newCat);
        }
    }
}
