/*
 * PlainTextEditor.java
 *
 * Created on 6 de Setembro de 2007, 18:17
 */

package org.gcreator.editors;

import org.gcreator.components.TabPanel;
import org.gcreator.components.*;
import org.gcreator.fileclass.*;
import javax.swing.*;

/**
 *
 * @author  Luís
 */
public class PlainTextEditor extends TabPanel {
    
    /** Creates new form PlainTextEditor */
    
    public org.gcreator.fileclass.File file;
    public boolean changed = false;
    
    public PlainTextEditor(org.gcreator.fileclass.File file,Project project) {
        this.project = project;
        initComponents();
        jTextPane1.setText((String) file.value);
        this.file = file;
    }
    
    @Override
    public boolean canSave(){
        return true;
    }
    
    @Override
    public boolean Save(){
        file.value = jTextPane1.getText();
        changed = false;
        return true;
    }
    
    @Override
    public void dispose(){
        if(!wasModified())
            super.dispose();
        else{
            java.lang.Object[] options = {"Yes",
                    "No",
                    "Cancel"};
            int n = JOptionPane.showOptionDialog(frame,
            "You have unsaved changes in your document.\n" +
            "Do you want to save it?",
            "Save document?",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);
            if(n==JOptionPane.YES_OPTION){
                if(Save())
                    super.dispose();
            }
            if(n==JOptionPane.NO_OPTION){
                super.dispose();
            }
        }
    }
    
    @Override
    public boolean wasModified(){
        return changed;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPane1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextPane1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyTyped
        changed = true;
    }//GEN-LAST:event_jTextPane1KeyTyped
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
    
}