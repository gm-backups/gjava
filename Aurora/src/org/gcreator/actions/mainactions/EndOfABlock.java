/*
 * StartOfABlock.java
 * 
 * Created on 26/Set/2007, 16:44:23
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.actions.mainactions;

import org.gcreator.editors.ActorEditor;
import org.gcreator.actions.*;
import org.gcreator.editors.*;
import javax.swing.*;

/**
 *
 * @author Luís
 */
public class EndOfABlock extends ActionPattern{
    public EndOfABlock(ActorEditor context){
        super(context);
        setStandardImage(new ImageIcon(getClass().getResource("/org/gcreator/actions/images/End_Block.png")));
    }
    
    @Override
    public String getStandardText(JComponent panel){
        return "End block";
    }
    
    @Override
    public String generateEGML(JComponent panel){
        return "}";
    }
}