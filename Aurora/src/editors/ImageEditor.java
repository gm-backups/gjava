/*
 * ImageEditor.java
 *
 * Created on 10 September 2007, 02:24
 */

package editors;

import javax.swing.*;

import components.*;

/**
 *
 * @author  Ali1
 */
public class ImageEditor extends TabPanel {
    
    /** Creates new form ImageEditor */
    private fileclass.File file;
    private boolean changed = false;
    
    public ImageEditor(fileclass.File file) {
        this.file = file;
        
        initComponents();
        if(file.value instanceof Icon){
            jLabel1.setIcon((ImageIcon) file.value);
        }
        jTextField1.setText(file.name);
    }
    
    @Override
    public boolean wasModified(){
        return changed;
    }
    
    @Override
    public boolean canSave(){
        return true;
    }
        
    @Override
    public boolean Save(){
        file.value = (ImageIcon) jLabel1.getIcon();
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
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jFileChooser1.setDialogTitle("Select a file");
        jFileChooser1.setDragEnabled(true);

        jButton1.setText(managers.LangSupporter.activeLang.getEntry(118));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jScrollPane1.setViewportView(jLabel1);

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
        jFileChooser1.showDialog(this, "OK");
        if(jFileChooser1.getSelectedFile()!=null){
            java.io.File _file = jFileChooser1.getSelectedFile();
            file.type = _file.getName().substring(_file.getName().lastIndexOf(".")+1);
            file.value = new ImageIcon(_file.getPath());
            jLabel1.setIcon((ImageIcon) file.value);
            System.out.println(file.name + "." + file.type);
        }
        }
        catch(Exception e){}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        file.name = jTextField1.getText();
        core.Aurwindow.workspace.updateUI();
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        file.name = jTextField1.getText();
        core.Aurwindow.workspace.updateUI();
    }//GEN-LAST:event_jTextField1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}