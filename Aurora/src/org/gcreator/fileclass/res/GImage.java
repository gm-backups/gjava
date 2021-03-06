/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.fileclass.res;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * 
 * <strike><strong>&#64;deprecated</strong> Use ImageIcon</strike>
 * @author Serge Humphrey
 */
public class GImage implements Resource {

    static final long serialVersionUID = 1L;
    public ImageIcon image;
    public boolean transparent;
    public Color transparentColor = Color.WHITE; 
    public int zoom;
    public ImageIcon transparentImage;
    
    public GImage() {
        image = null;
    }
    
    public GImage(ImageIcon i){
        image = i;
    }
        
    public ImageIcon getImage() {
        return image;
    }
    
    public ImageIcon getTransparentImage() {
        return transparentImage;
    }
    
    public String writeXml() {
      String xml = "";
      xml += "<?xml version=\"1.0\"?>\n";
      xml += "<gimage>\n";
      xml += "<transparent>"+transparent+"</transparent>\n";
      xml += "<color>"+transparentColor.getRed()+", "+transparentColor.getGreen()+", "+transparentColor.getBlue()+"</color>\n";
      xml += "<zoom>"+zoom+"</zoom>";
      xml += "</gimage>\n";
      return xml;
    }

     
    public void readXml(String xml){     
        String[] lines = xml.split("\n");
        String line;
        if(!lines[0].matches("<\\?xml version=\"1\\.0\"\\?>")) {
            return;
        }
        if(!lines[1].matches("<gimage>")) {
            return;
        }
        if(lines.length < 3) {
            return;
        }
        int i = 3;
        while(i < lines.length) {
            line = lines[i];
            System.out.println(line);
            if(line == null || line.equals("")) {
                continue;
            }
            if(line.equals("</gimage>")){
                break;
            }
            if (line.matches("<transparent>(true|false)</transparent>")) {
                String trans = line.replaceAll("<transparent>(true|false)</transparent>", "$1");
                transparent = Boolean.parseBoolean(trans);
            }
            else if (line.matches("<color>[0-9]+, [0-9]+, [0-9]+</color>")) {
                String sred = line.replaceAll("<color>([0-9]+), [0-9]+, [0-9]+</color>", "$1");
                String sgreen = line.replaceAll("<color>[0-9]+, ([0-9]+), [0-9]+</color>", "$1");
                String sblue = line.replaceAll("<color>[0-9]+, [0-9]+, ([0-9]+)</color>", "$1");
                int red = Integer.parseInt(sred);
                int green = Integer.parseInt(sgreen);
                int blue = Integer.parseInt(sblue);
                transparentColor = new Color(red, green, blue);
            }
            else if (line.matches("<zoom>[0-9]+</zoom>")) {
                String szoom = line.replaceAll("<zoom>([0-9]+)</zoom>", "$1");
                zoom = Integer.parseInt(szoom);
            }
            i++;
        }
    }
    
     
    public String exportToHtml(boolean xhtml) {
        return "";
    }
    
    @Override
    public Object clone() {
        GImage a = new GImage();
        a.image = new ImageIcon(image.getImage());
        a.transparent = transparent;
        a.transparentColor = transparentColor;
        a.transparentImage = new ImageIcon(transparentImage.getImage());
        return a;
    }
}
