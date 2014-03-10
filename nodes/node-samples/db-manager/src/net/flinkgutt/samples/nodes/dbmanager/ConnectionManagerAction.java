package net.flinkgutt.samples.nodes.dbmanager;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.prefs.BackingStoreException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.flinkgutt.samples.nodes.api.db.IDatabaseServerSettings;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DropDownButtonFactory;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

@ActionID(
        category = "File",
        id = "net.flinkgutt.samples.nodes.dbmanager.ConnectionManagerAction")
@ActionRegistration(
        displayName = "#CTL_ConnectionManagerAction",
        lazy = false)
@ActionReference(path = "Toolbars/Edit", position = 1300)
@Messages("CTL_ConnectionManagerAction=Connection Manager")
public final class ConnectionManagerAction extends AbstractAction implements Presenter.Toolbar {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ConnectionManagerAction->actionPerformed.");
        ConnectionManager manager = new ConnectionManager();
        JButton exitButton = new JButton(NbBundle.getMessage(ConnectionManagerAction.class, "Button.exit.text"));
        exitButton.setIcon(ImageUtilities.loadImageIcon("net/flinkgutt/samples/nodes/dbmanager/1369435876_exit.png", false));
        DialogDescriptor dialogDescriptor = new DialogDescriptor(manager, NbBundle.getMessage(ConnectionManagerAction.class, "ConnectionManagerAction.dialog.title.text"), true, new Object[]{exitButton}, null, DialogDescriptor.BOTTOM_ALIGN, null, null);
        DialogDisplayer.getDefault().notifyLater(dialogDescriptor);
    }

    private static JPopupMenu popup;
    private static JButton dropDownButton;
    private MyQuickServerItemListener menuItemListener;
    private static ServerStoreService serverStore;

    @Override
    public Component getToolbarPresenter() {
        if (serverStore == null) {
            serverStore = new ServerStoreService();
        }
        //initialize JPopupMenu:
        popup = new JPopupMenu();

        //initialize ActionListener:
        menuItemListener = new MyQuickServerItemListener();

        try {
            for (IDatabaseServerSettings server : ServerStoreService.getConfiguredServers()) {
                JMenuItem item = new JMenuItem(server.getDisplayName());
                item.setActionCommand(server.getStoredID());
                item.addActionListener(menuItemListener);
                popup.add(item);
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        // Use the org.openide.awt.DropDownButtonFactory to create a DropDownButton,
        // define the icon and then assign our JPopupMenu to the JButton:
        dropDownButton = DropDownButtonFactory.createDropDownButton(
                ImageUtilities.loadImageIcon("net/flinkgutt/samples/nodes/dbmanager/1369227877_server.png", false),
                popup);

        //the JButton will be quite long by default, 
        //let's trim it by removing borders:
        dropDownButton.setBorder(javax.swing.BorderFactory.
                createEmptyBorder(1, 1, 1, 1));
        dropDownButton.addActionListener(this);
        return dropDownButton;
    }
}
