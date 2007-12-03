/*
 * RSSReader.java
 *
 * Created on 23 de Novembro de 2007, 20:22
 */

package org.gcreator.components;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author  Luís
 */
public class Reader extends javax.swing.JPanel {
    
    Vector<String[]> elements = new Vector<String[]>();
    
    /** Creates new form RSSReader */
    @SuppressWarnings("empty-statement")
    public Reader(String url) {
        initComponents();
        try{
            System.out.println(url);
            URL _url = new URL(url);
            URLConnection con = _url.openConnection();
            System.out.println("connection was open");
            InputStream stream = con.getInputStream();
            System.out.println("Locating");
            while(true){
                if(stream.read()=='<')
                    if(stream.read()=='o')
                        if(stream.read()=='l')
                            if(stream.read()=='>'){
                                int qq = stream.read();
                                if(qq=='\n'||(qq=='\r'&&stream.read()=='\n'))
                                    qq = stream.read();
                                    if(qq=='\n'||(qq=='\r'&&stream.read()=='\n'))
                                        break;
                            }
            }
            mainLoop:
            while(true){
                int fc = stream.read();
                int t = stream.read();
                if(t=='/'){
                    break mainLoop;
                }
                while(stream.read()!='a')
                    ;
                while(stream.read()!='\'')
                    ;
                String link = "";
                int linkchar;
                while((linkchar = stream.read()) != '\'')
                    link += (char) linkchar;
                while(stream.read()!='>')
                    ;
                String x = "";
                int q;
                while((q = stream.read()) !='<'){
                    x += (char) q;
                }
                while(stream.read()!='\n')
                    ;
                x = x.replaceAll("&amp;", "&");
                x = x.replaceAll("&lt:", "<");
                x = x.replaceAll("&gt;", ">");
                x = x.replaceAll("&#39;", "'");
                x = x.replaceAll("&#33;", "!");
                System.out.println("Adding " + x + " with link " + link);
                elements.add(new String[]{x, link});
            }
            stream.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    public void paint(Graphics g){
        ((Graphics2D) g).setPaint(new GradientPaint(new Point(-100,-100), Color.PINK, new Point(getWidth()+100, getHeight()+100), Color.BLACK));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        for(int i = 0; i < elements.size(); i++){
            g.drawString(elements.get(i)[0], 0, 15*i);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 334, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 283, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}