/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.components.popupmenus;

import org.gcreator.editors.SceneEditor;
import javax.swing.*;
import org.gcreator.managers.*;
import java.awt.event.*;
import org.gcreator.core.*;
import org.gcreator.editors.*;
import org.gcreator.fileclass.res.*;

/**
 *
 * @author luis
 */
public class ScenePopupMenu extends JPopupMenu{
    public JCheckBoxMenuItem grid;
    private SceneEditor res;
    public ScenePopupMenu(SceneEditor res){
        this.res = res;
        grid = new JCheckBoxMenuItem("Display grid");
        grid.setSelected(((Scene) res.file.value).grid);
        grid.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                grid();
            }
        });
        this.add(grid);
        this.updateUI();
    }
    public void setVisible(boolean v){
        if(v==true)
            grid.setSelected(((Scene) res.file.value).grid);
        super.setVisible(v);
    }
    public void grid(){
        ((Scene) res.file.value).grid = grid.isSelected();
        res.jToggleButton1.setSelected(grid.isSelected());
        res.scenePanel.updateUI();
    }
}
