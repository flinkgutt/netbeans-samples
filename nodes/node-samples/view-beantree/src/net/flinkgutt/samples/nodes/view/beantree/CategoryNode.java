package net.flinkgutt.samples.nodes.view.beantree;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import net.flinkgutt.samples.nodes.api.ICategory;
import net.flinkgutt.samples.nodes.api.ICategoryDAO;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;

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
        super(Children.create(new CategoryChildFactory(category), true), Lookups.singleton(category));
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
                new CreateCategoryAction(),
                new RenameCategoryAction(),
                new RemoveCategoryAction()
            };
        } else {
            return new Action[]{};
        }
    }

    private class CreateCategoryAction extends AbstractAction {

        public CreateCategoryAction() {
            putValue(NAME, NbBundle.getMessage(CategoryNode.class, "Action.createCategory.label"));
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            NotifyDescriptor.InputLine input = new NotifyDescriptor.InputLine(
                    NbBundle.getMessage(CategoryNode.class, "Action.createCategory.name.label"),
                    NbBundle.getMessage(CategoryNode.class, "Action.createCategory.title"));
            Object result = DialogDisplayer.getDefault().notify(input);
            if (result != NotifyDescriptor.OK_OPTION) {
                return;
            }
            String categoryName = input.getInputText();
            // TODO Simplify the API some here
            ICategory newCat = categoryDAO.createCategory(category, categoryName);
            categoryDAO.addCategory(newCat);
            category.addChild(newCat);
        }
    }

    private class RenameCategoryAction extends AbstractAction {

        public RenameCategoryAction() {
            putValue(NAME, NbBundle.getMessage(CategoryNode.class, "Action.renameCategory.label"));
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            NotifyDescriptor.InputLine input = new NotifyDescriptor.InputLine(
                    NbBundle.getMessage(CategoryNode.class, "Action.renameCategory.name.label"),
                    NbBundle.getMessage(CategoryNode.class, "Action.renameCategory.title"));
            input.setInputText(category.getName()); // Setting the inputfield to the current name
            Object result = DialogDisplayer.getDefault().notify(input);
            if (result != NotifyDescriptor.OK_OPTION) {
                return;
            }
            String oldName = category.getName();
            category.setName(input.getInputText());
            categoryDAO.update(category);
            fireNameChange(oldName, input.getInputText());
        }
    }

    private class RemoveCategoryAction extends AbstractAction {

        public RemoveCategoryAction() {
            putValue(NAME, NbBundle.getMessage(CategoryNode.class, "Action.removeCategory.label"));
        }

        @Override
        public void actionPerformed(ActionEvent ae) {

            NotifyDescriptor.Confirmation confirm = new NotifyDescriptor.Confirmation(
                    NbBundle.getMessage(CategoryNode.class, "Action.removeCategory.areyousure"),
                    NbBundle.getMessage(CategoryNode.class, "Action.removeCategory.title"),
                    NotifyDescriptor.YES_NO_OPTION);

            Object result = DialogDisplayer.getDefault().notify(confirm);
            if (result != NotifyDescriptor.YES_OPTION) {
                return;
            }
            
            if (categoryDAO.hasChildren(category)) {
                NotifyDescriptor.Message msg = new NotifyDescriptor.Message(NbBundle.getMessage(CategoryNode.class, "Action.removeCategory.notEmpty"),NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notifyLater(msg);
            } else {
                category.getParent().removeChild(category);
                categoryDAO.deleteCategory(category);
            }
        }
    }
}
