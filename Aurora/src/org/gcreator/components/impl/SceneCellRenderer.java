/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.components.impl;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.gcreator.fileclass.Project;
import org.gcreator.fileclass.res.Scene;

public class SceneCellRenderer extends JLabel implements ListCellRenderer {

    private static final long serialVersionUID = 1;
    private Project p;

    public SceneCellRenderer(Project p) {
        this.p = p;
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list,
            Object value,
            int i,
            boolean selected,
            boolean focus) {
        ImageIcon icon = null;
        String val = value.toString();

        boolean invalid = false;
        if (!(value instanceof Integer)) {
            invalid = true;
            val = "Invalid scene";
            if (selected) {
                setForeground(Color.YELLOW);
            } else {
                setForeground(Color.RED);
            }
        } else {
            Object o = p.getFileFor((Integer) value).value;
            if (!(o instanceof Scene)) {
                invalid = true;
                val = "Invalid scene";
                setForeground(Color.RED);
            } else {
                val = p.getFileFor((Integer) value).name;
            }
        }

        if (selected && !invalid) {
            try {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } catch (Exception e) {
                setBackground(Color.BLUE);
                setForeground(Color.WHITE);
            }
        } else {
            try {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            } catch (Exception e) {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
        }

        setText(val);
        setIcon(icon);

        return this;
    }
}
