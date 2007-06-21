package org.gjava.welcome;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import org.openide.awt.HtmlBrowser;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
final class welcomeformTopComponent extends TopComponent {
    
    private static welcomeformTopComponent instance;
    /** path to the icon used by the component and its open action */
    //    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    
    private static final String PREFERRED_ID = "welcomeformTopComponent";
    
    private welcomeformTopComponent() {
        initComponents();
        setName(org.openide.util.NbBundle.getMessage(org.gjava.welcome.welcomeformTopComponent.class, "CTL_welcomeformTopComponent"));
        setToolTipText(org.openide.util.NbBundle.getMessage(org.gjava.welcome.welcomeformTopComponent.class, "HINT_welcomeformTopComponent"));        
//        
//        HtmlBrowser h = new HtmlBrowser();
//        h.setHomePage("http://forums.g-java.com") ;
//               // this.add(h);
//                h.setVisible(true);
//                
//                org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
//            .add(h, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
//            .add(org.jdesktop.layout.GroupLayout.TRAILING, h, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
//        );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        welcomePanel1 = new org.gjava.welcome.html.WelcomePanel();

        org.openide.awt.Mnemonics.setLocalizedText(jButton6, "jButton6");

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "Welcome to G-java!");

        jScrollPane1.setViewportView(welcomePanel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .add(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.gjava.welcome.html.WelcomePanel welcomePanel1;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized welcomeformTopComponent getDefault() {
        if (instance == null) {
            instance = new welcomeformTopComponent();
        }
        return instance;
    }
    
    /**
     * Obtain the welcomeformTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized welcomeformTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(welcomeformTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof welcomeformTopComponent) {
            return (welcomeformTopComponent)win;
        }
        Logger.getLogger(welcomeformTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }
    
    public void componentOpened() {
        // TODO add custom code on component opening
    }
    
    public void componentClosed() {
        // TODO add custom code on component closing
    }
    
    /** replaces this in object stream */
    public Object writeReplace() {
        return new ResolvableHelper();
    }
    
    protected String preferredID() {
        return PREFERRED_ID;
    }
    
    final static class ResolvableHelper implements Serializable {
        private static final long serialVersionUID = 1L;
        public Object readResolve() {
            return welcomeformTopComponent.getDefault();
        }
    }
    
}
