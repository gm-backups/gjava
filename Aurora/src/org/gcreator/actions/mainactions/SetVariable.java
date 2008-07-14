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

import org.gcreator.actions.*;
import javax.swing.*;
import com.l2fprod.common.propertysheet.*;
import org.gcreator.components.PropertyManager;
import org.gcreator.fileclass.Project;
import org.gcreator.managers.LangSupporter;

/**
 *
 * @author Luís, TGMG
 */
public class SetVariable extends ActionPattern {

    static final long serialVersionUID = 1L;
    public String to = "0.0";
    public String var = "x";
    public Boolean relative = false;
    public static ImageIcon icon =
            new ImageIcon(
            SetVariable.class.getResource("/org/gcreator/actions/images/Setvar.png"));

//    private static final ObjectStreamField[] serialPersistentFields
//                 = {new ObjectStreamField(
//      "text", String.class)};
    public SetVariable() {
        super();
    }

    @Override
    public void load(JComponent panel) {
        Property[] plist = ((PropertySheetPanel) panel).getProperties();
        for (int i = 0; i < plist.length; i++) {
            Property p = plist[i];
            if (p.getName().equals("to")) {
                p.setValue(to);
            } else if (p.getName().equals("var")) {
                p.setValue(var);
            } else if (p.getName().equals("relative")) {
                p.setValue(relative);
            }
        }
    //((HSpeedEditor) panel).to.setText(to);
    //((HSpeedEditor) panel).of.setText(with);
    //System.out.println("TEXT LOADED AS:"+to);
    }

    @Override
    public void save(JComponent panel) {
        Property[] plist = ((PropertySheetPanel) panel).getProperties();
        for (int i = 0; i < plist.length; i++) {
            Property p = plist[i];
            if (p.getName().equals("to")) {
                to = (String) p.getValue();
            } else if (p.getName().equals("var")) {
                var = (String) p.getValue();
            } else if (p.getName().equals("relative")) {
                relative = (Boolean) p.getValue();
            }
        }
    //to = ((HSpeedEditor) panel).to.getText();
    //with = ((HSpeedEditor) panel).of.getText();
    //System.out.println("text saved as:"+text);
    }

    public ImageIcon getStandardImage() {
        return icon;
    }

    public void setStandardImage(ImageIcon img) {
        icon = img;
    }

    public JComponent createNewPanel(org.gcreator.actions.Action action, Project project) {
        PropertyManager propertySheetPanel1 = new PropertyManager();
        /*final PropertyEditorFactory f = propertySheetPanel1.getEditorFactory();
        propertySheetPanel1.setEditorFactory(new PropertyEditorFactory() {

            public PropertyEditor createPropertyEditor(Property arg0) {
                if(arg0.getType()==org.gcreator.actions.components.FailureBehavior.class)
                    return new org.gcreator.actions.components.FailureBehaviorEditor();
                return f.createPropertyEditor(arg0);
            }
        });*/

        DefaultProperty p = new DefaultProperty();
        p.setCategory("<html><b>Main");
        p.setName("var");
        p.setDisplayName("Variable");
        p.setEditable(true);
        p.setType(String.class);
        p.setValue("x");
        p.setShortDescription("The variable to modify");
        propertySheetPanel1.addProperty(p);

        p = new DefaultProperty();
        p.setCategory("<html><b>Main");
        p.setName("to");
        p.setDisplayName("Value");
        p.setEditable(true);
        p.setType(String.class);
        p.setValue("0.0");
        p.setShortDescription("The value expression");
        propertySheetPanel1.addProperty(p);

        p = new DefaultProperty();
        p.setCategory("<html><b>Main");
        p.setName("relative");
        p.setDisplayName("Relative");
        p.setEditable(true);
        p.setType(Boolean.class);
        p.setValue(false);
        p.setShortDescription("Is the new value absolute or relative to the old one.");
        propertySheetPanel1.addProperty(p);
        
        //p = new DefaultProperty();
        //p.setCategory("<html><b>Useless");
        //p.setName("test");
        //p.setDisplayName("test");
        //p.setEditable(true);
        //p.setType(org.gcreator.actions.components.FailureBehavior.class);
        //org.gcreator.actions.components.FailureBehavior f2 = new org.gcreator.actions.components.FailureBehavior(1);
        //p.setValue(f2);
        //p.setShortDescription("Is the new value absolute or relative to the old one.");
        //propertySheetPanel1.addProperty(p);

        return propertySheetPanel1;
    }

    public String getStandardText(JComponent panel) {
        if (panel != null && panel instanceof PropertySheetPanel) {
            save(panel);
            PropertySheetPanel editor = (PropertySheetPanel) panel;
            String who = var;
            String what = to;
            Property[] plist = ((PropertySheetPanel) panel).getProperties();
            return "Set $apply to $value".replaceAll("\\$apply", who).replaceAll("\\$value", what).replaceAll("\\$\\$", "$");
        }
        return "Set variable";
    }

    public String generateGCL(JComponent panel) {
        if (panel != null && panel instanceof PropertySheetPanel) {
            PropertySheetPanel editor = (PropertySheetPanel) panel;
            save(panel);
            if (relative) {
                return var + " += (" + to + ");";
            } else {
                return var + " = (" + to + ");";
            }
        }
        return "";
    }
}
