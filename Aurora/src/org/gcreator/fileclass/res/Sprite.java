/*
 * Sprite.java
 * 
 * Created on 13-Sep-2007, 07:50:32
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.fileclass.res;

import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author Ali1
 */
public class Sprite extends Resource {
    public Vector<org.gcreator.fileclass.File> images;
    public int width,height,originX,originY,BBleft,BBRight,BBTop,BBBottom;
    public boolean precise = true;
    
    public Sprite(String name)
    {
        this.name = name;
        images = new Vector<org.gcreator.fileclass.File>();
    }

    public ImageIcon getImageAt(int pos){
        if(pos>=images.size())
            return null;
        org.gcreator.fileclass.File a = images.elementAt(pos);
        if(a==null)
            return null;
        if(a.value instanceof ImageIcon)
            return (ImageIcon) images.elementAt(pos).value;
        return null;
    }
    
    public int countImages(){
        return images.size();
    }
    
    public void addToList(org.gcreator.fileclass.File i){
        images.add(i);
    }
    
    @Override
    public String writeXml() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void readXml(String xml) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String exportToHtml(boolean xhtml){
        return "";
    }
    
    @SuppressWarnings("unchecked")
    public Resource clone(){
        Sprite a = new Sprite(name);
        a.BBBottom = BBBottom;
        a.BBRight = BBRight;
        a.BBTop = BBTop;
        a.BBleft = BBleft;
        a.height = height;
        a.images = (Vector<org.gcreator.fileclass.File>) images.clone();
        a.originX = originX;
        a.originY = originY;
        a.precise = precise;
        a.width = width;
        return a;
    }
}