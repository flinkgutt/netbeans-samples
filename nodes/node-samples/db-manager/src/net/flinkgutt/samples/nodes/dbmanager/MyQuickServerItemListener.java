package net.flinkgutt.samples.nodes.dbmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.JMenuItem;
import net.flinkgutt.samples.nodes.api.db.ConnectionAttemptReturnObject;
import net.flinkgutt.samples.nodes.api.db.IConnectionService;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author Christian
 */
class MyQuickServerItemListener implements ActionListener {

    private static final IConnectionService service = Lookup.getDefault().lookup(IConnectionService.class);

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            String serverID = ((JMenuItem) e.getSource()).getActionCommand();
            ConnectionAttemptReturnObject result = null;
            String errorMessage = null;
            try {
                result = service.connect(ServerStoreService.getServer(serverID));
            } catch (Exception ex) { // This is bad, don't do this.
                errorMessage = ex.getMessage();
            } finally {
                // if there is no exception thrown, but we still couldn't connect we should fetch the error message.
                if(errorMessage == null && result != null && !result.isSuccessful()) {
                    errorMessage = result.getErrorMessage();
                } 
                
                // finally we check to see if there is actually an error message and we then display something if there is
                if(errorMessage != null){
                    NotifyDescriptor nd = new NotifyDescriptor.Message(NbBundle.getMessage(ConnectionManager.class, "ConnectionManager.connect.failure", errorMessage), NotifyDescriptor.ERROR_MESSAGE);
                    DialogDisplayer.getDefault().notifyLater(nd);
                }
            }
        }

    }
}
