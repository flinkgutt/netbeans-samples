/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.dbmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
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

    ConnectionManager manager = new ConnectionManager();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        
        DialogDescriptor dialogDescriptor = new DialogDescriptor(manager, "Connection Manager");
        DialogDisplayer.getDefault().notify(dialogDescriptor);
        
    }
}
