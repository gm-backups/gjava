/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.editors;

import org.gcreator.components.SubimagePreview;
import org.gcreator.components.TabPanel;
import org.gcreator.components.resource.*;
import org.gcreator.fileclass.Project;
import org.gcreator.fileclass.res.Sprite;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.gcreator.components.GetImageResizeInfoDialog;
import org.gcreator.components.GetValueDialog;
import org.gcreator.components.IconList;
import org.gcreator.core.gcreator;
import org.gcreator.fileclass.Folder;
import org.gcreator.fileclass.GFile;
import org.gcreator.fileclass.GameProject;
import org.gcreator.fileclass.ModuleProject;
import org.gcreator.units.Dictionary;
import org.gcreator.units.ObjectNode;
/**
 *
 * @author  TGMG
 * @author Luís Reis
 * @author Serge Humphrey
 */
public class SpriteEditor extends TabPanel {

    public static final long serialVersionUID = 1;
    public Sprite sprite;
    public boolean changed;
    private SubimagePreview prev;
    private ResourceChooser res;
    private boolean dragging = false;

    private enum Bounds {
        FULL, AUTO_STRICT, AUTO_STANDARD, AUTO_CUSTOM, CUSTOM
    };
    private  Bounds bounds;

    public  IconList subimageList; /** Creates new form SpriteEditor
             * @param file The file that the sprite belongs to.
             * @param project The project that the sprite belongs to.
             */
        public SpriteEditor(org.gcreator.fileclass.GFile file, Project project) {
        this.project = project;
        this.file = file;
        boolean dontChange = false;
        if (file.value != null) {
            if (file.value instanceof String) {
                Sprite spr = new Sprite(project);
                spr.readXml((String) file.value);
                file.value = spr;
                changed = true;
            } else {
                this.sprite = (Sprite) ((Sprite) file.value).clone();
                changed = false;
                dontChange = true;
            }
        } else {
            this.sprite = new Sprite(project);
            changed = true;
        }
        Component c = org.gcreator.core.gcreator.panel.getNavigatorPanel();
        if (c instanceof JPanel) {
            ((JPanel) c).updateUI();
        } else if (c instanceof JScrollPane) {
            ((JScrollPane) c).updateUI();
            Component d = ((JScrollPane) c).getViewport().getView();
            if (d instanceof JPanel) {
                ((JPanel) d).updateUI();
            } else if (d instanceof JEditorPane) {
                ((JEditorPane) d).updateUI();
            } else {
                d.repaint();
            }
        }
        initComponents();
        load();
        try {
            jTextField1.setText(file.name);
        } catch (NullPointerException e) {
        }
        jScrollPane1.setViewportView(prev = new SubimagePreview(this));

        jPanel13.add(res = new ResourceChooser(project, "image"));

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent evt) {
                updateName();
            }

            @Override
            public void insertUpdate(DocumentEvent evt) {
                updateName();
            }

            @Override
            public void removeUpdate(DocumentEvent evt) {
                updateName();
            }
        });
        if (dontChange) {
            changed = false;
        }

        subimageList = new IconList();
        subimageList.setColumns(5);
        subimageList.addRunnable(new Runnable() {

            @Override
            public void run() {
                try {
                    setViewedId((Integer) subimageList.getSelectedExtraContent()-1);
                    prev.repaint();
                } catch (NullPointerException exc) {
                    System.err.println("NullPointerException caught@run()in SpriteEditor$x: " + exc);
                }
            }
        });
        if (file.value != null) {
            for (int i = 0; i < sprite.countImages(); i++) {
                GFile f = sprite.getAt(i);
                Image img = ((ImageIcon) f.value).getImage();
                subimageList.addElement("Subimage " + (i + 1), new ImageIcon(img.getScaledInstance(100, -1, Image.SCALE_DEFAULT)), i + 1);
            }
        }
        jScrollPane2.setViewportView(subimageList);
    }

    public void updateName() {
        file.name = jTextField1.getText();
        //sprite.name = file.name;
        org.gcreator.core.gcreator.panel.workspace.updateUI();
    }

    @Override
    public boolean canSave() {
        System.out.println("canSave: " + changed);
        return changed;
    }

    @Override
    public boolean Save() {
        changed = false;
        sprite.BBBottom = (Integer) jSpinner6.getValue();
        sprite.BBRight = (Integer) jSpinner5.getValue();
        sprite.BBTop = (Integer) jSpinner4.getValue();
        sprite.BBLeft = (Integer) jSpinner3.getValue();

        sprite.originX = (Integer) jSpinner1.getValue();
        sprite.originY = (Integer) jSpinner2.getValue();

        sprite.precise = jCheckBox1.isSelected();

        file.value = sprite;

        return true;
    }

    @Override
    public boolean wasModified() {
        System.out.println("wasModified: " + changed);
        return changed;
    }

    public void load() {
        if (sprite == null) {
            this.sprite = new Sprite(project/*file.name*/);
            return;
        }
        jSpinner1.setValue(sprite.originX);
        jSpinner2.setValue(sprite.originY);

        jSpinner6.setValue(sprite.BBBottom);
        jSpinner5.setValue(sprite.BBRight);
        jSpinner4.setValue(sprite.BBTop);
        jSpinner3.setValue(sprite.BBLeft);

        jCheckBox1.setSelected(sprite.precise);
        jLabel2.setText("Width: " + sprite.width);
        jLabel3.setText("Height: " + sprite.height);
        jLabel4.setText("Subimages: " + sprite.countImages());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jSpinner4 = new javax.swing.JSpinner();
        jSpinner5 = new javax.swing.JSpinner();
        jSpinner6 = new javax.swing.JSpinner();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        saveResourcePanel1 = new org.gcreator.components.SaveResourcePanel(this);
        jScrollPane2 = new javax.swing.JScrollPane();

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setDividerSize(8);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.99);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseReleased(evt);
            }
        });
        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseDragged(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));

        jPanel13.setLayout(new java.awt.BorderLayout());

        jButton2.setText(Dictionary.getEntry("editors-sprite-addimage"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Height:");

        jLabel2.setText("Width:");

        jLabel4.setText("Subimages:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gcreator/resources/spriteeditor/subimageright.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("Show:");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/gcreator/resources/spriteeditor/subimageleft.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("0");

        jButton1.setText(Dictionary.getEntry("editors-sprite-remove"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2))
                .addGap(12, 12, 12)
                .addComponent(jButton1)
                .addGap(23, 23, 23))
        );

        jTabbedPane1.addTab("General", jPanel4);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Bounding Box"));

        jLabel8.setText("Left:");

        jLabel9.setText("Right:");

        jLabel10.setText("Top:");

        jLabel11.setText("Bottom:");

        jSpinner3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner3StateChanged(evt);
            }
        });

        jSpinner4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner4StateChanged(evt);
            }
        });

        jSpinner5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner5StateChanged(evt);
            }
        });

        jSpinner6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner6StateChanged(evt);
            }
        });

        jButton8.setText("Automatic (Strict)");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Full Image");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton5.setText("Automatic (Standard)");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("Automatic (Custom)");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinner6, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jSpinner4, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jSpinner5, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)))
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jSpinner6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Precise collision checking");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox1))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Collision", jPanel5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Origin"));

        jLabel6.setText("X:");

        jLabel7.setText("Y:");

        jButton6.setText("Center");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Other", jPanel6);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Name:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, 0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jSplitPane1.setTopComponent(jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(saveResourcePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
            .addComponent(saveResourcePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel8);

        jScrollPane3.setViewportView(jSplitPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 536, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        file.name = jTextField1.getText();
        org.gcreator.core.gcreator.panel.workspace.updateUI();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
        changed = true;
        sprite.originY = (Integer) jSpinner2.getValue();
        jScrollPane1.updateUI();
    }//GEN-LAST:event_jSpinner2StateChanged

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        changed = true;
        sprite.originX = (Integer) jSpinner1.getValue();
        jScrollPane1.updateUI();
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        changed = true;
        jSpinner1.setValue(sprite.width / 2);
        jSpinner2.setValue(sprite.height / 2);
        sprite.originX = (Integer) jSpinner1.getValue();
        sprite.originY = (Integer) jSpinner2.getValue();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        changed = true;
        sprite.precise = jCheckBox1.isSelected();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        bounds = SpriteEditor.Bounds.AUTO_CUSTOM;
        setAutomaticBounds();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        bounds = SpriteEditor.Bounds.AUTO_STANDARD;
        setAutomaticBounds();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        changed = true;
        bounds = SpriteEditor.Bounds.FULL;
        setAutomaticBounds();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        bounds = SpriteEditor.Bounds.AUTO_STRICT;
        setAutomaticBounds();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jSpinner6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner6StateChanged
        changed = true;
        bounds = SpriteEditor.Bounds.CUSTOM;
        sprite.BBBottom = (Integer) jSpinner6.getValue();
        jScrollPane1.updateUI();
    }//GEN-LAST:event_jSpinner6StateChanged

    private void jSpinner5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner5StateChanged
        changed = true;
        bounds = SpriteEditor.Bounds.CUSTOM;
        sprite.BBRight = (Integer) jSpinner5.getValue();
        jScrollPane1.updateUI();
    }//GEN-LAST:event_jSpinner5StateChanged

    private void jSpinner4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner4StateChanged
        changed = true;
        bounds = SpriteEditor.Bounds.CUSTOM;
        sprite.BBTop = (Integer) jSpinner4.getValue();
        jScrollPane1.updateUI();
    }//GEN-LAST:event_jSpinner4StateChanged

    private void jSpinner3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner3StateChanged
        changed = true;
        bounds = SpriteEditor.Bounds.CUSTOM;
        sprite.BBLeft = (Integer) jSpinner3.getValue();
        jScrollPane1.updateUI();
    }//GEN-LAST:event_jSpinner3StateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        changed = true;
        int id = getViewedId();
        sprite.Simages.remove(id);
        subimageList.removeElement(id);
        cleanSubimages();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (sprite.countImages() == 0) {
            return;
        }
        int id = (getViewedId() + 1) % sprite.countImages();
        setViewedId(id);
        subimageList.setSelectedIndex(id);
        subimageList.repaint();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int id = getViewedId() - 1;
        if (id < 0) {
            id = Math.max(0, sprite.countImages() - 1);
        }
        setViewedId(id);
        subimageList.setSelectedIndex(id);
        subimageList.repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        org.gcreator.fileclass.GFile a = res.getFile();
        if (a == null || a.value == null) {
            return;
        }
        changed = true;
        Object o = a.value;
        if (sprite.countImages() == 0) {
            if (o != null) {
                sprite.width = ((ImageIcon) o).getIconWidth();
                sprite.height = ((ImageIcon) o).getIconHeight();
                jLabel2.setText("Width: " + sprite.width);
                jLabel3.setText("Height: " + sprite.height);
            } else {
                sprite.width = 0;
                sprite.height = 0;
            }
            jButton9ActionPerformed(evt);
        }

        if (o != null &&
                (sprite.width == ((ImageIcon) o).getIconWidth()) &&
                sprite.height == ((ImageIcon) o).getIconHeight()) {
            sprite.addToList(res.getFile());
        } else if (o != null) {
            ImageIcon newImg = (ImageIcon) o;
            GetImageResizeInfoDialog dialog = new GetImageResizeInfoDialog(gcreator.window, project);
            if (dialog.canceled) {
                return;
            }
            BufferedImage bufImg = null;
            //<editor-fold desc="Use new size">
            if (dialog.size == GetImageResizeInfoDialog.CanvasSize.NEW) {
                bufImg = new BufferedImage(newImg.getIconWidth(),
                        newImg.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

                for (int i = 0; i < sprite.countImages(); i++) {
                    bufImg.getGraphics().drawImage(sprite.getImageIconAt(i).getImage(), 0, 0, sprite.getImageIconAt(i).getImageObserver());
                    sprite.setImageIconAt(i, new ImageIcon(bufImg));// TODO: figure out why this changes the origanal sprite, not the cloned one
                    //!!BAD PROGRAMMING ALERT:
                    bufImg = new BufferedImage(newImg.getIconWidth(),
                            newImg.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);//Any good way to clear bufImg? flush() doesn't seem to work.
                    /*
                 * Debugging
                 *
                JFrame f = new JFrame("Flushed");
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.add(new JLabel(new ImageIcon(bufImg)));
                f.pack();
                f.setVisible(true);
                 */
                }
                sprite.width = newImg.getIconWidth();
                sprite.height = newImg.getIconHeight();
                sprite.addToList(res.getFile());
            }//</editor-fold>
            //<editor-fold desc="Use original size">
            else if (dialog.size == GetImageResizeInfoDialog.CanvasSize.ORIGINAL) {
                bufImg = new BufferedImage(sprite.width, sprite.height, BufferedImage.TYPE_4BYTE_ABGR);
                //<editor-fold desc="Stretch">
                if (dialog.imgScale == GetImageResizeInfoDialog.ImageScale.STRETCH) {
                    if (dialog.aspect) {
                        if (newImg.getIconWidth() > newImg.getIconHeight()) {
                            bufImg.getGraphics().drawImage(newImg.getImage().
                                    getScaledInstance(sprite.width, -1, Image.SCALE_SMOOTH), 0, 0, null);
                        } else {
                            bufImg.getGraphics().drawImage(newImg.getImage().
                                    getScaledInstance(-1, sprite.height, Image.SCALE_SMOOTH), 0, 0, null);
                        }
                    } else {
                        bufImg.getGraphics().drawImage(newImg.getImage().
                                getScaledInstance(sprite.width, sprite.height, Image.SCALE_SMOOTH), 0, 0, null);
                    }
                }//</editor-fold>
                //<editor-fold desc="Else">
                else {
                    Point p = getLocationFor(dialog.imgScale, new Dimension(sprite.width, sprite.height), new Dimension(newImg.getIconWidth(), newImg.getIconHeight()));
                    bufImg.getGraphics().drawImage(newImg.getImage(), p.x, p.y, newImg.getImageObserver());
                }//</editor-fold>
                GFile f;
                if (dialog.alteration == dialog.alteration.MESS_UP) {
                    res.getFile().value = new ImageIcon(bufImg);
                    f = res.getFile();
                } else {
                    Folder folder = project.getFolderFor("image");
                    GFile tempFile = new GFile(folder, dialog.newFileName, res.getFile().type, null);
                    ObjectNode node = new ObjectNode(tempFile);
                    folder.node.add(node);
                    gcreator.panel.workspace.updateUI();
                    if (project instanceof GameProject) {
                        ((GameProject) project).images++;
                    } else if (project instanceof ModuleProject) {
                        ((ModuleProject) project).images++;
                    } else {
                        System.out.println("Error : spriteeditor-4453_bob");
                        return;
                    }
                    tempFile.value = new ImageIcon(bufImg);
                    f = tempFile;
                }
                sprite.addToList(f);
            }//</editor-fold>
            //<editor-fold desc="Use maximal size">
            else if (dialog.size == GetImageResizeInfoDialog.CanvasSize.MAXIMAL) {
                int width = Math.max(sprite.width, newImg.getIconWidth());
                int height = Math.max(sprite.height, newImg.getIconHeight());
                bufImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                //<editor-fold desc="Resize all the images">
                for (int i = 0; i < sprite.countImages(); i++) {
                    bufImg.getGraphics().drawImage(sprite.getImageIconAt(i).getImage(), 0, 0, sprite.getImageIconAt(i).getImageObserver());
                    sprite.setImageIconAt(i, new ImageIcon(bufImg));// TODO: figure out why this changes the origanal sprite, not the cloned one
                    bufImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);//Any good way to clear bufImg? flush() doesn't seem to work.
                }//</editor-fold>
                //<editor-fold desc="if the option isn't 'Stretched'">
                if (dialog.imgScale != GetImageResizeInfoDialog.ImageScale.STRETCH) {
                    Point p = getLocationFor(dialog.imgScale, new Dimension(width, height), new Dimension(newImg.getIconWidth(), newImg.getIconHeight()));
                    bufImg.getGraphics().drawImage(newImg.getImage(), p.x, p.y, newImg.getImageObserver());
                }//</editor-fold>
                //<editor-fold desc="if not, ">
                else {
                    if (dialog.aspect) {
                        if (newImg.getIconWidth() > newImg.getIconHeight()) {
                            bufImg.getGraphics().drawImage(newImg.getImage().
                                    getScaledInstance(width, -1, Image.SCALE_SMOOTH), 0, 0, null);
                        } else {
                            bufImg.getGraphics().drawImage(newImg.getImage().
                                    getScaledInstance(-1, height, Image.SCALE_SMOOTH), 0, 0, null);
                        }
                    } else {
                        bufImg.getGraphics().drawImage(newImg.getImage().
                                getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
                    }
                }//</editor-fold>
                sprite.width = width;
                sprite.height = height;
                GFile f;
                if (dialog.alteration == dialog.alteration.MESS_UP) {
                    res.getFile().value = new ImageIcon(bufImg);
                    f = res.getFile();
                } else {
                    Folder folder = project.getFolderFor("image");
                    GFile tempFile = new GFile(folder, dialog.newFileName, res.getFile().type, null);
                    ObjectNode node = new ObjectNode(tempFile);
                    folder.node.add(node);
                    gcreator.panel.workspace.updateUI();
                    if (project instanceof GameProject) {
                        ((GameProject) project).images++;
                    } else if (project instanceof ModuleProject) {
                        ((ModuleProject) project).images++;
                    } else {
                        System.out.println("Error : spriteeditor-4456_bob");
                        return;
                    }
                    //GImage g = new GImage();
                    tempFile.value = new ImageIcon(bufImg);
                    f = tempFile;
                }
                sprite.addToList(f);
            }//</editor-fold>
        }
        cleanSubimages();
        bounds = SpriteEditor.Bounds.FULL;
        setAutomaticBounds();
        jScrollPane1.updateUI();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jScrollPane1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseDragged
        changed = true;
        if (!dragging) {
            return;
        }
        int x, y;
        x = evt.getX() - 2;
        y = evt.getY() - 2;
        sprite.originX = x;
        sprite.originY = y;
        jSpinner1.setValue(x);
        jSpinner2.setValue(y);
        prev.repaint();
    }//GEN-LAST:event_jScrollPane1MouseDragged

    private void jScrollPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseReleased
        changed = true;
        dragging = false;
    }//GEN-LAST:event_jScrollPane1MouseReleased

    private void jScrollPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MousePressed
        changed = true;
        if (evt.getX() > sprite.width || evt.getY() > sprite.height) {
            return;
        }
        dragging = true;
        jScrollPane1MouseDragged(evt);
    }//GEN-LAST:event_jScrollPane1MousePressed

    private Point getLocationFor(GetImageResizeInfoDialog.ImageScale scale, Dimension containerSize, Dimension thingSize) {
        int x = 0, y = 0;
        int width = thingSize.width;
        int height = thingSize.height;
        if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_TOP_LEFT ||
                scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_NORTHWEST) {
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_CENTER) {
            x = containerSize.width / 2 - width / 2;
            y = containerSize.height / 2 - height / 2;
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_NORTH) {
            System.gc();
            x = containerSize.width / 2 - width / 2;
            y = 0;
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_NORTHEAST) {
            x = containerSize.width / 2 - width;
            y = 0;
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_EAST) {
            x = containerSize.width - width;
            y = containerSize.height / 2 - height / 2;
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_SOUTHEAST) {
            x = containerSize.width - width;
            y = containerSize.height - height;
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_SOUTH) {
            x = containerSize.width / 2 - width / 2;
            y = containerSize.height - height;
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_SOUTHWEST) {
            x = 0;
            y = containerSize.height - height;
        } else if (scale == GetImageResizeInfoDialog.ImageScale.PLACE_AT_WEST) {
            x = 0;
            y = containerSize.height / 2 - height / 2;
        }
        return new Point(x, y);
    }

    private void cleanSubimages() {
        int id = getViewedId();
        if (id >= sprite.countImages()) {
            id = 0;
        }
        jLabel4.setText("Subimages: " + sprite.countImages());
        setViewedId(id);
        /*while (subimageList.countVisibleElements() > sprite.countImages()) {
        subimageList.removeElement(subimageList.countVisibleElements()-1);
        }*/
        for (int i = 0; i < sprite.countImages(); i++) {
            if (i >= subimageList.countVisibleElements()) {
                subimageList.addElement(null, null, null);
            }
            subimageList.setElementImage(i, new ImageIcon(sprite.getImageIconAt(i).getImage().
                    getScaledInstance(Math.min(100, sprite.getImageIconAt(i).getIconWidth()), -1, Image.SCALE_DEFAULT)));
            subimageList.setElementText(i, "Subimage " + (i + 1));
            subimageList.setElementExtraContent(i, new Integer(i));
        }
        jScrollPane2.updateUI();
        subimageList.repaint();
    }

    public int getViewedId() {
        try {
            return Integer.parseInt(jTextField2.getText());
        } catch (Throwable t) {
            return -1;
        }
    }

    public void setViewedId(int id) {
        jTextField2.setText("" + id);
        jScrollPane1.updateUI();
    }

    public ImageIcon getImageAt(int id) {
        //if(id<sprite.countImages())
        //    return null;
        try {
            return sprite.getImageIconAt(id);
        } catch (NullPointerException exc) {
            return null;
        }
    }

    public ImageIcon getCurrentImage() {
        return getImageAt(getViewedId());
    }

    private void setAutomaticBounds() {
        if (sprite.countImages() <= 0) {
            return;
        }
        changed = true;
        final int MIN_ALPHA;
        if (bounds == Bounds.FULL) {
            jSpinner3.setValue(sprite.width - 1);//left
            jSpinner4.setValue(0);//top
            jSpinner5.setValue(0);//right
            jSpinner6.setValue(sprite.height - 1);//bottom
            sprite.BBLeft = (Integer) jSpinner3.getValue();
            sprite.BBTop = (Integer) jSpinner4.getValue();
            sprite.BBRight = (Integer) jSpinner5.getValue();
            sprite.BBBottom = (Integer) jSpinner6.getValue();
            return;
        } else {
            if (bounds == Bounds.AUTO_STANDARD) {
                MIN_ALPHA = (int) (0.5 * 255);
            } else if (bounds == Bounds.AUTO_STRICT) {
                MIN_ALPHA = (int) (0.8 * 255);
            } else {// Bounds.AUTO_CUSTOM
                GetValueDialog d = new GetValueDialog(gcreator.window, 0, 255, 128,
                        Dictionary.getEntry("editors-sprite-boundtransparency"));
                MIN_ALPHA = d.getValue().intValue();
                d.dispose();
            }
        }

        BufferedImage bufImg = new BufferedImage(sprite.width, sprite.height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bufImg.createGraphics();
        boolean setl = false, sett = false, setr = false, setb = false;
        for (int n = 0; n < sprite.countImages(); n++) {
            Image img;
            Color transparentColor = null;
            try {
                ImageIcon gimg = sprite.getImageIconAt(n);
                img = gimg.getImage();
            } catch (NullPointerException e) {
                System.out.println("[SpriteEditor@FixAutomaticBounds:862]NullPointerException!");
                return;
            }
            g.drawImage(img, 0, 0, null);
            //left
            for (int i = 0; i < ((setl) ? sprite.BBLeft : sprite.width); i++) {
                for (int j = 0; j < sprite.height; j++) {
                    int RGBA = bufImg.getRGB(i, j);
                    Color col = new Color(RGBA, true);
                    if (!col.equals(transparentColor) && col.getAlpha() >= MIN_ALPHA) {
                        sprite.BBLeft = i;
                        setl = true;
                        break;
                    }
                }
            }
            //right
            for (int i = sprite.width - 1; i >= ((setr) ? sprite.BBRight : 0); i--) {
                for (int j = 0; j < sprite.height; j++) {
                    int RGBA = bufImg.getRGB(i, j);
                    Color col = new Color(RGBA, true);
                    if (!col.equals(transparentColor) && col.getAlpha() >= MIN_ALPHA) {
                        sprite.BBRight = i;
                        setr = true;
                        break;
                    }
                }
            }
            //top
            for (int i = 0; i < ((sett) ? sprite.BBTop : sprite.height); i++) {
                for (int j = 0; j < sprite.width; j++) {
                    int RGBA = bufImg.getRGB(j, i);
                    Color col = new Color(RGBA, true);
                    if (!col.equals(transparentColor) && col.getAlpha() >= MIN_ALPHA) {
                        sprite.BBTop = i;
                        sett = true;
                        break;
                    }
                }
            }
            //bottom
            for (int i = sprite.height - 1; i >= ((setb) ? sprite.BBBottom : 0); i--) {
                for (int j = 0; j < sprite.width; j++) {
                    int RGBA = bufImg.getRGB(j, i);
                    Color col = new Color(RGBA, true);
                    if (!col.equals(transparentColor) && col.getAlpha() >= MIN_ALPHA) {
                        sprite.BBBottom = i;
                        setb = true;
                        break;
                    }
                }
            }
            if (n + 1 < sprite.countImages()) {
                bufImg = new BufferedImage(sprite.width, sprite.height, BufferedImage.TYPE_4BYTE_ABGR);
                g = bufImg.createGraphics();
            }
        }
        System.gc();
        load();
        prev.repaint();

    }
    
    @Override
    public boolean setModified(boolean a) {
        changed = a;
        return true;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Variables declaration - do not modify"> 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JSpinner jSpinner1;
    public javax.swing.JSpinner jSpinner2;
    public javax.swing.JSpinner jSpinner3;
    public javax.swing.JSpinner jSpinner4;
    public javax.swing.JSpinner jSpinner5;
    public javax.swing.JSpinner jSpinner6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private org.gcreator.components.SaveResourcePanel saveResourcePanel1;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
