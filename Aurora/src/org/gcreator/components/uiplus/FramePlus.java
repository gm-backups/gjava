/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.components.uiplus;

import java.awt.Container;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author luis
 */
public class FramePlus extends JFrame{
    private float alpha = 1.0f;
    public FramePlus(){
        setContentPane(new JPanel());
    }
    public void setWindowMask(Icon mask){
        com.sun.jna.examples.WindowUtils.setWindowMask(this, mask);
    }
    public void setDoubleBuffered(boolean b){
        Container c = getContentPane();
        try{
        c.getClass().getMethod("setDoubleBuffered", Boolean.TYPE).invoke(c, b);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public void setWindowAlpha(float alpha){
        com.sun.jna.examples.WindowUtils.setWindowAlpha(this, alpha);
        this.alpha = alpha;
    }
    
    public float getWindowAlpha(){
        return alpha;
    }
    
    public void fadeOut(){
        fadeOut(5);
    }
    
    public void fadeOut(final long delay){
        Thread t = new Thread(){
            public void run(){
                if(com.sun.jna.examples.WindowUtils.isWindowAlphaSupported()){
                    while(alpha>=0.01){
                        alpha-=0.01;
                        setWindowAlpha(alpha);
                        try{
                            sleep(delay);
                        }
                        catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                    dispose();
                }
                else{
                    dispose();
                }
            }
        };
        t.start();
    }
}