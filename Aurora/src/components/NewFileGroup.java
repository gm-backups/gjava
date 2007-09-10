/*
 * NewFileGroup.java
 *
 * Created on 6 de Setembro de 2007, 11:20
 */

package components;

import units.ObjectNode;
import javax.swing.*;
import javax.swing.tree.*;

import core.*;

/**
 *
 * @author  Luís
 */

abstract class Model extends AbstractListModel{
    public String getTrueName(int i){
        return null;
    }
}

public class NewFileGroup extends TabPanel {
      
    public JTree tree;
    
    /** Creates new form NewFileGroup */
    
    public Model a1;
    public Model a2;
    public Model a3;
    
    public NewFileGroup() {
        initComponents();
        tree = new JTree(aurora.window.top);
        jScrollPane1.setViewportView(tree);
        DefaultTreeSelectionModel model = new DefaultTreeSelectionModel();
        model.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setSelectionModel(model);
        tree.setCellRenderer(aurora.window.renderer);
        a1 = new Model() {
            String[] strings = {"Group", "Text file"};
            String[] truenames = {"Group", "Text"};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
            public String getTrueName(int i){ return truenames[i]; }
        };
        a2 = new Model() {
            String[] strings = {"Sprite", "Actor", "Scene", "EGML Script"};
            String[] truenames = {"Sprite", "Actor", "Scene", "EGML"};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
            public String getTrueName(int i){ return truenames[i]; }
        };
        a3 = new Model() {
            String[] strings = {"Java file", "C++ file", "C++ header file", "XML file"};
            String[] truenames = {"Java", "C++", "C++ header", "XML"};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
            public String getTrueName(int i){ return truenames[i]; }
        };
        jList2.setModel(a1);
        jList1.setSelectedIndex(0);
        //tree.addMouse
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        jLabel1.setText(managers.LangSupporter.activeLang.getEntry(95));

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { managers.LangSupporter.activeLang.getEntry(97), "Game items", "Development items" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jList2);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))))
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String val = ((Model) jList2.getModel()).getTrueName(jList2.getSelectedIndex());
        if(getCurrentObject() instanceof fileclass.Folder)
            add(val);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if(jList1.getSelectedIndex()==0)
            jList2.setModel(a1);
        else if(jList1.getSelectedIndex()==1)
            jList2.setModel(a2);
        else if(jList1.getSelectedIndex()==2)
            jList2.setModel(a3);
    }//GEN-LAST:event_jList1ValueChanged
    
    public fileclass.Folder getCurrentFolder() {
        if (getCurrentObject() == null) {
            return null; //null for none
        }
        if (getCurrentObject() instanceof fileclass.Folder) {
            return (fileclass.Folder) getCurrentObject();
        }
        if (getCurrentObject() instanceof fileclass.File) {
            return ((fileclass.File) getCurrentObject()).root;
        }
        return null;
    }

    public fileclass.Object getCurrentObject() {
        //Currently selected object
        if (tree.getSelectionCount() != 1) {
            return null;
        }
        TreePath selection = tree.getSelectionPath();
        if (!(selection.getLastPathComponent() instanceof ObjectNode)) {
            return null;
        }
        ObjectNode node = (ObjectNode) selection.getLastPathComponent();
        return node.object;
    }
    
    public void add(String val){
        String name = jTextField1.getText();
        if(name.equals(""))
            name = "newGroup";
        if(val.equals("Group"))
            aurora.window.addGroup(getCurrentFolder(), getCurrentFolder().newGroup(name));
        if(val.equals("Text"))
            aurora.window.addFile(getCurrentFolder(), name, "txt");
        if(val.equals("Sprite"))
            aurora.window.addFile(getCurrentFolder(), name, "sprite");
        if(val.equals("Actor"))
            aurora.window.addFile(getCurrentFolder(), name, "actor");
        if(val.equals("Scene"))
            aurora.window.addFile(getCurrentFolder(), name, "scene");
        if(val.equals("EGML"))
            aurora.window.addFile(getCurrentFolder(), name, "egml");
        if(val.equals("Java"))
            aurora.window.addFile(getCurrentFolder(), name, "java");
        if(val.equals("C++"))
            aurora.window.addFile(getCurrentFolder(), name, "cpp");
        if(val.equals("C++ header"))
            aurora.window.addFile(getCurrentFolder(), name, "h");
        if(val.equals("XML"))
            aurora.window.addFile(getCurrentFolder(), name, "xml");
        
        aurora.window.addWindow(new NewFileGroup(), 96);
        this.dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}
