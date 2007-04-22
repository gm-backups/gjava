/*
 * Room.java
 *
 * Created on 14 April 2007, 22:43
 */

package org.JGM.roomeditor;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.gjava.actoreditor.Utilz;
import org.gjava.actoreditor.actorData;
import org.openide.windows.TopComponent;

/**
 *
 * @author  ali1
 */
public class Room extends TopComponent {

    // public RoomPanel canvas = new RoomPanel(this);

    protected void componentClosed() {
        
        int decision = JOptionPane.showConfirmDialog(null, "Would you like to save changes to the Room?");
    if (decision == JOptionPane.YES_OPTION)
    {
        //save room
    }
    else if (decision == JOptionPane.NO_OPTION)
    {
      //leave room
        
    }
        super.componentClosed();
    }

    /** Creates new form Room */
    public Room() {
        
        initComponents();
//         jScrollPane3.setViewportView(canvas);
        setDropTarget(new DropTarget(this,new DropTargetListener()
        {
            public void dragEnter(DropTargetDragEvent dropTargetDragEvent)
            {
                //not needed
            }
            public void dragExit(DropTargetEvent dropTargetEvent)
            {
                //not needed
            }
            public void dragOver(DropTargetDragEvent dtde)
            {
                if( dtde.isDataFlavorSupported(Utilz.ACTOR_DATA_FLAVOR ) )
                {
                    //only accept object of our type
                    dtde.acceptDrag( DnDConstants.ACTION_COPY_OR_MOVE );
                }
                else
                {
                    //reject everything else
                    dtde.rejectDrag();
                }
            }
            public void drop(DropTargetDropEvent dtde)
            {
                //first check if we support this type of data
                if( !dtde.isDataFlavorSupported( Utilz.ACTOR_DATA_FLAVOR ) )
                {
                    dtde.rejectDrop();
                }
                //accept the drop so that we can access the Transferable
                dtde.acceptDrop( DnDConstants.ACTION_COPY_OR_MOVE );
                actorData data = null;
                try
                {
                    //get the dragged data from the transferable
                    //get the dragged data from the transferable
                    //get the dragged data from the transferable
                    //get the dragged data from the transferable
                    data = (actorData) dtde.getTransferable().getTransferData(Utilz.ACTOR_DATA_FLAVOR);
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
                catch (UnsupportedFlavorException ex)
                {
                    ex.printStackTrace();
                }

                dtde.dropComplete( null != data );
                if( null != data )
                {

                }
                 if (data.img == null)
                 {
                        // jLabel2.setIcon(new ImageIcon("object.png"));
                         System.out.println("obj.pmng");
                          jLabel2.setText(data.name);
                 }
                     else
                     {
                   jLabel2.setIcon(new ImageIcon(data.img));
                   jLabel2.setText(data.name);
                   System.out.println(data.img);
                     }
            }



            public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent)
            {
            }
        }));
    }

     public static Room getInstance(String name) {
        // look for an open instance containing this feed
        Iterator opened = TopComponent.getRegistry().getOpened().iterator();
        while (opened.hasNext()) {
            Object tc = opened.next();
            if (tc instanceof Room) {
                Room elc = (Room)tc;
                if (name.equals(elc.getName())) {
                    //elc.requestActive();
                    return elc;
                }
            }
        }
        // none found, make a new one
        return new Room();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();

        jButton2.setText("jButton2");

        setLayout(new java.awt.BorderLayout());

        jLabel2.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jLabel2.text")); // NOI18N
        jScrollPane2.setViewportView(jLabel2);

        jCheckBox1.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jCheckBox1.text")); // NOI18N
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel1.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jLabel1.text")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jCheckBox1)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                        .add(12, 12, 12))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 12, Short.MAX_VALUE)
                .add(jCheckBox1)
                .addContainerGap())
        );

        jTabbedPane2.addTab(org.openide.util.NbBundle.getMessage(Room.class, "Room.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jLabel3.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jTextField1.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jLabel4.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jLabel5.text")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jTextField2.text")); // NOI18N
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
        });

        jTextField3.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jTextField3.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jLabel6.text")); // NOI18N

        jTextField4.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jTextField4.text")); // NOI18N

        jButton1.setText(org.openide.util.NbBundle.getMessage(Room.class, "Room.jButton1.text")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .add(jLabel3)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel5)
                            .add(jLabel4)
                            .add(jLabel6))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .add(jTextField3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .add(jTextField4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(17, 17, 17)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(14, 14, 14)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(27, 27, 27)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(36, 36, 36)
                .add(jButton1)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab(org.openide.util.NbBundle.getMessage(Room.class, "Room.jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        jSplitPane1.setLeftComponent(jTabbedPane2);
        jSplitPane1.setRightComponent(jScrollPane3);

        add(jSplitPane1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
    // System.out.println("Focus gained");
}//GEN-LAST:event_jTextField2FocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

}