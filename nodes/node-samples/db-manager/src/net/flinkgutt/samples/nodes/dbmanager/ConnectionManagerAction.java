package net.flinkgutt.samples.nodes.dbmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "net.flinkgutt.samples.nodes.dbmanager.ConnectionManagerAction")
@ActionRegistration(
        iconBase = "net/flinkgutt/samples/nodes/dbmanager/1369227877_server.png",
        displayName = "#CTL_ConnectionManagerAction")
@ActionReference(path = "Toolbars/Edit", position = 1300)
@Messages("CTL_ConnectionManagerAction=Connection Manager")
public final class ConnectionManagerAction implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        ConnectionManager manager = new ConnectionManager();
        JButton exitButton = new JButton(NbBundle.getMessage(ConnectionManagerAction.class, "Button.exit.text"));
        exitButton.setIcon(ImageUtilities.loadImageIcon("net/flinkgutt/samples/nodes/dbmanager/1369435876_exit.png",false));
        DialogDescriptor dialogDescriptor = new DialogDescriptor(manager, NbBundle.getMessage(ConnectionManagerAction.class,"ConnectionManagerAction.dialog.title.text"), true,new Object[]{exitButton},null,DialogDescriptor.BOTTOM_ALIGN, null, null);
 
        DialogDisplayer.getDefault().notifyLater(dialogDescriptor);
        
    }
}
