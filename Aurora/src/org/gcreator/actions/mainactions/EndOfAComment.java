/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.actions.mainactions;

import java.util.Vector;
import org.gcreator.actions.*;
import javax.swing.*;

/**
 *
 * @author Luís
 */
public class EndOfAComment extends ActionPattern{
    
    private static ImageIcon icon = new ImageIcon(EndOfAComment.class.getResource("/org/gcreator/actions/images/endcomment.png"));
    public EndOfAComment(){
        super();
    }
    
     
    @Override
    public String getStandardText(JComponent panel){
        return "End of a comment";
    }
    @Override
    public void setStandardImage(ImageIcon icon){
        EndOfAComment.icon = icon;
    }
     
    @Override
    public String generateGCL(JComponent panel){
        return "*/";
    }
    @Override
     public ImageIcon getStandardImage() {
        return icon;
    }
     
    @Override
     public boolean unindentsNext(JComponent comp, Vector<org.gcreator.actions.Action> indented, Vector<org.gcreator.actions.Action> unindented, JList list, boolean selected){
         return true;
     }
}
