/*
 * RSSReadPanel.java
 *
 * Created on 22 de Novembro de 2007, 21:44
 */

package org.gcreator.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author  Luís
 */
public class RSSBlogReadPanel extends JPanel {
    
    /** Creates new form BeanForm */
    public RSSBlogReadPanel() {
        initComponents();
        jScrollPane1.setViewportView(new RSSReader(""));
    }
    
    //http://forums.g-java.com/index.php?automodule=blog&req=syndicate
    public void paint(Graphics g){
        super.paint(g);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recent user blog entries", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255)));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setOpaque(false);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
