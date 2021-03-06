package net.flinkgutt.samples.nodes.view.reorder;

import javax.swing.Action;
import net.flinkgutt.samples.nodes.api.ICategory;
import org.openide.actions.ReorderAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Christian
 */
public class RootNode extends AbstractNode {

    public RootNode(final ICategory category, InstanceContent ic) {
        super(Children.create(new CategoryChildFactory(category), true), new AbstractLookup(ic));

        ic.add(new Index.Support() {
            @Override
            public Node[] getNodes() {
                return getChildren().getNodes(true);
            }

            @Override
            public int getNodesCount() {
                return getNodes().length;
            }

            @Override
            public void reorder(int[] perm) {
                category.reorder(perm);
            }
        });
    }

    @Override
    public Action[] getActions(boolean context) {
        Action reorderAction = SystemAction.get(ReorderAction.class);
        return new Action[]{reorderAction};
    }
}
