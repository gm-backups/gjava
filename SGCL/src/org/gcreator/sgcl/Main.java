/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.sgcl;

import java.io.FileReader;
import java.util.Vector;
import org.antlr.runtime.Token;
import org.gcreator.sgcl.example.JavaManager;

/**
 *
 * @author luis
 */
public class Main implements SGCLManager{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            JavaManager m = new JavaManager(5);
            SGCLTranslator t
                    = new SGCLTranslator(Main.class.getResourceAsStream("/org/gcreator/sgcl/test1.sgcl"), m);
            t.parse();
            System.out.println(m.getText());
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public void outputCode(Vector<Token> tokens){
        for(Token t : tokens){
            System.out.println("Type: " + t.getType() + "=" + t.getText());
        }
    }
    
    public boolean supportsExtension(String extension){
        if(extension.equals("System.Extensions.Generics"))
            return true;
        return false;
    }

}
