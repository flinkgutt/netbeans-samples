package net.flinkgutt.samples.nodes.view.beantree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.flinkgutt.samples.nodes.api.ICategoryDAO;
import net.flinkgutt.samples.nodes.api.db.IConnectionService;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.WeakListeners;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//net.flinkgutt.samples.nodes.view.beantree//BeanTree//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "BeanTreeTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "net.flinkgutt.samples.nodes.view.beantree.BeanTreeTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_BeanTreeAction",
        preferredID = "BeanTreeTopComponent")
@Messages({
    "CTL_BeanTreeAction=BeanTree",
    "CTL_BeanTreeTopComponent=BeanTree Window",
    "HINT_BeanTreeTopComponent=This is a BeanTree window"
})
public final class BeanTreeTopComponent extends TopComponent implements ExplorerManager.Provider, PropertyChangeListener {

    private ICategoryDAO categoryDAO = Lookup.getDefault().lookup(ICategoryDAO.class);
    private ExplorerManager em = new ExplorerManager();
    private IConnectionService connection = Lookup.getDefault().lookup(IConnectionService.class);
    
    public BeanTreeTopComponent() {
        initComponents();
        setName(Bundle.CTL_BeanTreeTopComponent());
        setToolTipText(Bundle.HINT_BeanTreeTopComponent());
        
        categoryBeanTreeView.setRootVisible(false);
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
    }
    
    private void refreshTree() {
        // We check if we actually found an implementation of ICategoryDAO
        if(categoryDAO == null) { 
            return; // Or we could try one more time to find an implementation
        }
        em.setRootContext(new CategoryNode(categoryDAO.getRootCategory()));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        categoryBeanTreeView = new org.openide.explorer.view.BeanTreeView();
        refreshButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(BeanTreeTopComponent.class, "BeanTreeTopComponent.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(categoryBeanTreeView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(refreshButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(categoryBeanTreeView, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton)
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refreshTree();
    }//GEN-LAST:event_refreshButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.view.BeanTreeView categoryBeanTreeView;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void componentOpened() {
        connection.addPropertyChangeListener("connection",WeakListeners.propertyChange(this,connection));
        // If we are connected, start anything that needs to be started
        if( connection.isConnected()) {
            refreshTree();
        }
    }

    @Override
    public void componentClosed() {
        connection.removePropertyChangelistener("connection", this);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // if we have just connected to the db, use it!
        if(evt.getPropertyName().equals("connection")) {
            refreshTree();
        }
    }

}
