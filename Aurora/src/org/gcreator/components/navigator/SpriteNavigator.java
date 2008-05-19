/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.components.navigator;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.gcreator.fileclass.GFile;
import org.gcreator.fileclass.res.GImage;
import org.gcreator.fileclass.res.Sprite;

/**
 *
 * @author luis
 */
public class SpriteNavigator extends JScrollPane{
    private GFile file;
    private ImagePreviewer p;
    
    public GFile getFile(){
        return file;
    }
    
    public Sprite getSprite(){
        GFile f = getFile();
        if(f.value==null) return null;
        if(!(f.value instanceof Sprite)) return null;
        return ((Sprite) f.value);
    }
    
    public SpriteNavigator(GFile file){
        this.file = file;
        p = new ImagePreviewer();
        this.setViewportView(p);
        Thread t = new Thread(){
            public void run(){
                try{
                    int i = 0;
                    while(true){
                        Sprite s = getSprite();
                        if(s==null){
                            p.i = null;
                            sleep(200);
                            continue;
                        }
                        else{
                           // System.out.println("There are " + s.Simages.size());
                            if(i >= s.Simages.size())
                                i = 0;
                            p.i = ((GImage) (((GFile) s.Simages.get(i)).value)).image;
                            updateUI();
                            repaint();
                          //  System.out.println("Updating to " + i);
                            sleep(100);
                            i++;
                            continue;
                        }
                    }
                }
                catch(Exception e){
                    
                }
            }
        };
        t.start();
        setVisible(true);
        setDoubleBuffered(true);
    }
    
    private class ImagePreviewer extends JPanel{
        private ImageIcon i = null;
        private ImagePreviewer(){
            setDoubleBuffered(true);
        }
        
        public void paint(Graphics g){
            super.paint(g);
            
            if(i!=null){
                g.drawImage(i.getImage(), 0, 0, i.getImageObserver());
            }
        }
    }
}
