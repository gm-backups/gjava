/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.autocomplete.impl;

import java.awt.Color;
import javax.swing.ImageIcon;


/**
 * @author Luís Reis
 */
public class ClassSuggestion extends Suggestion {

    private String text = "";
    private static ImageIcon img = new ImageIcon(ClassSuggestion.class.getResource("/org/gcreator/resources/i_class.png"));

    public ClassSuggestion() {
    }

    public ClassSuggestion(String text) {
        this.text = text;
    }

    @Override
    public Color getForeground() {
        return new Color(0, 150, 0);
    }

    @Override
    public ImageIcon getImage() {
        return img;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String confirm(String context, String prevWord) {
        try {
            return (text + (prevWord.equals("new") ? "(" : "")).substring(context.substring(context.lastIndexOf('.') + 1).length());
        } catch (Exception e) {
            return "";
        }
    }
}
