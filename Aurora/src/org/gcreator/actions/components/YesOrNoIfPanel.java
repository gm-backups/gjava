/*
 * YesOrNoIfPanel.java
 *
 * Created on 27 de Dezembro de 2007, 16:52
 */

package org.gcreator.actions.components;

import org.gcreator.managers.*;

/**
 *
 * @author  luis
 */
public class YesOrNoIfPanel extends javax.swing.JPanel {
    
    /** Creates new form YesOrNoIfPanel */
    public YesOrNoIfPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NotCheckbox = new javax.swing.JCheckBox();

        NotCheckbox.setText(LangSupporter.activeLang.getEntry(243));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NotCheckbox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NotCheckbox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox NotCheckbox;
    // End of variables declaration//GEN-END:variables
    
}
