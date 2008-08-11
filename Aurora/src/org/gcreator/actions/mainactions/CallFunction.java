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
import org.gcreator.actions.components.ArgumentList;
import org.gcreator.components.PropertyManager;
import org.gcreator.fileclass.Project;
import org.gcreator.managers.LangSupporter;

/**
 *
 * @author Luís, TGMG
 */
public class CallFunction extends ActionPattern {

    static final long serialVersionUID = 1L;
    public String ret = "x";
    public String fname = "f";
    public Boolean relative = false;//,question=false;
    public ArgumentList args = new ArgumentList();
    public static ImageIcon icon = new ImageIcon(CallFunction.class.getResource("/org/gcreator/actions/images/function.png"));;
    

//    private static final ObjectStreamField[] serialPersistentFields
//                 = {new ObjectStreamField(
//      "text", String.class)};
    public CallFunction() {
        super();
    }

    @Override
    public void load(JComponent panel) {
        Property[] plist = ((PropertySheetPanel) panel).getProperties();
        for (int i = 0; i < plist.length; i++) {
            Property p = plist[i];
            if (p.getName().equals("ret")) {
                p.setValue(ret);
            } else if (p.getName().equals("fname")) {
                p.setValue(fname);
            } else if (p.getName().equals("args")) {
                p.setValue(args);
            } else if (p.getName().equals("relative")) {
                p.setValue(relative);
            }
//            } else if (p.getName().equals("question")) {
//                p.setValue(question);
//            }
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
            if (p.getName().equals("ret")) {
                ret = (String) p.getValue();
            } else if (p.getName().equals("fname")) {
                fname = (String) p.getValue();
            } else if (p.getName().equals("args")) {
                args = (ArgumentList) p.getValue();
            } else if (p.getName().equals("relative")) {
                relative = (Boolean) p.getValue();
            }
//            else if (p.getName().equals("question")) {
//                question = (Boolean) p.getValue();
//            }
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
        p.setName("ret");
        p.setDisplayName("Return");
        p.setEditable(true);
        p.setType(String.class);
        p.setValue("");
        p.setShortDescription("The variable to store the return value.<br>Leave empty for none");
        propertySheetPanel1.addProperty(p);
        
        p = new DefaultProperty();
        p.setCategory("<html><b>Main");
        p.setName("fname");
        p.setDisplayName("Function");
        p.setEditable(true);
        p.setType(String.class);
        p.setValue(fname);
        p.setShortDescription("The function to call");
        propertySheetPanel1.addProperty(p);
        
        p = new DefaultProperty();
        p.setCategory("<html><b>Main");
        p.setName("args");
        p.setDisplayName("Arguments");
        p.setEditable(true);
        p.setType(ArgumentList.class);
        p.setValue(args);
        p.setShortDescription("The arguments of the function");
        propertySheetPanel1.addProperty(p);
        
        p = new DefaultProperty();
        p.setCategory("<html><b>Main");
        p.setName("relative");
        p.setDisplayName("Relative?");
        p.setEditable(true);
        p.setType(Boolean.class);
        p.setValue(relative);
        p.setShortDescription("Should the result of the function be relative to previous value?");
        propertySheetPanel1.addProperty(p);

//        p = new DefaultProperty();
//        p.setCategory("<html><b>Main");
//        p.setName("question");
//        p.setDisplayName("Question?");
//        p.setEditable(true);
//        p.setType(Boolean.class);
//        p.setValue(relative);
//        p.setShortDescription("Is this a question? If so it will be treated as an if statement.");
//        propertySheetPanel1.addProperty(p);

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
            return "Call $func".replaceAll("\\$func", fname);
        }
        return "Call function";
    }

    public String generateGCL(JComponent panel) {
        if (panel != null && panel instanceof PropertySheetPanel) {
            PropertySheetPanel editor = (PropertySheetPanel) panel;
            save(panel);
            String rel="{",endrel="}";
            if (this.relative){
                rel+="argument_relative=true;";
            endrel="argument_relative=false;}";
            }
//            if (this.question)
//                return "if ("+ fname + "("+args.toString()+","+this.relative+"))";
            if (ret.equals("")) {
                return rel+" "+ fname + "("+args.toString()+")}";
            } else {
                return rel+" "+ret + " = " + fname + "("+args.toString()+")"+endrel;
            }
        }
        return "";
    }
}
