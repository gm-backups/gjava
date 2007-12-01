/*
 * ImageDisplayer.java
 *
 * Created on 30 de Setembro de 2007, 18:48
 */
package org.gcreator.components;

import java.awt.*;
import javax.swing.*;
import org.gcreator.editors.ImageEditor;

/**
 *
 * @author  Luís
 */
public class ImageDisplayer extends javax.swing.JPanel {

    /** Creates new form ImageDisplayer */
    private org.gcreator.fileclass.File file;
    public double zoom = 1;
    public ImageEditor e;

    public ImageDisplayer(ImageEditor e, org.gcreator.fileclass.File file) {
        initComponents();
        this.file = file;
        this.e = e;
    }

     
    public int getWidth() {
        if (file.value == null) {
            return 0;
        }
        return (int) (((ImageIcon) file.value).getIconWidth() * zoom);
    }

     
    public int getHeight() {
        if (file.value == null) {
            return 0;
        }
        return (int) (((ImageIcon) file.value).getIconHeight() * zoom);
    }

     
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

     
    public void paint(Graphics g) {

        //super.paint(g);
        boolean isBlack = true;
//        if (e.jToggleButton1.isSelected()) {
//            for (int i = 0; i <= getWidth() / 10; i++) {
//                boolean isNBlack = isBlack;
//                for (int j = 0; j <= getHeight() / 10; j++) {
//                    if ((isBlack = !isBlack)) {
//                        g.setColor(Color.WHITE);
//                    } else {
//                        g.setColor(Color.LIGHT_GRAY);
//                    }
//                    g.fillRect(i * 10 + 1, j * 10 + 1, 10, 10);
//                }
//                isBlack = !isNBlack;
//            }
//        }
//        if (e.jToggleButton2.isSelected()) {
//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, getWidth(), getHeight());
//        }
//        if (e.jToggleButton3.isSelected()) {
//            g.setColor(Color.GRAY);
//            g.fillRect(0, 0, getWidth(), getHeight());
//        }
//        if (e.jToggleButton4.isSelected()) {
//            g.setColor(Color.BLACK);
//            g.fillRect(0, 0, getWidth(), getHeight());
//        }
//        if(e.jToggleButton5.isSelected()){
//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, getWidth(), getHeight());
//            g.setColor(Color.BLACK);
//            for(int i = 0; i * 20 < getWidth(); i++)
//                g.drawLine(i * 20, 0, i * 20, getHeight());
//            for(int i = 0; i * 20 < getHeight(); i++)
//                g.drawLine(0, i * 20, getWidth(), i * 20);
//        }
        g.setColor(e.getTransparencyColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (file.value != null) {
            g.drawImage(((ImageIcon) file.value).getImage(), 0, 0, getWidth(), getHeight(), ((ImageIcon) file.value).getImageObserver());
        }
    }

    /* 
    public void paintComponents(Graphics g){
        super.paintComponent(g);
        
    }*/
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 134, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 132, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
