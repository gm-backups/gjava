/*
 * aurora.java
 * 
 * Created on 24/Ago/2007, 13:32:12
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.core;

import org.gcreator.editors.ActorEditor;
import org.gcreator.plugins.*;
import org.gcreator.managers.*;
import org.gcreator.clipboard.*;
import org.gcreator.languages.*;

import javax.swing.*;
import org.gcreator.components.NewFileGroup;
import org.gcreator.components.NewProject;
import org.gcreator.help.AboutPanel;

/**
 *
 * @author Luís
 */
public class gcreator {
    private static String[] arguments;
    public static final String version = "1.0alpha"; 
    public static Aurwindow window;
    public static String output = "";
    private static SplashScreen splash;
    public static PluginCore[] plugins;
    public static ClipboardManager clipboard = new ClipboardManager();
    public static String folder;
    
    public static String[] getargs(){
        return arguments;
    }
    
    public static void main(String[] args){
        folder = "" + gcreator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		int location = folder.lastIndexOf("/");
		folder = folder.substring(0,location+1);
		folder = folder.replaceAll("file://","");
		folder = folder.replaceAll("%20"," ");
		folder = folder.substring(1);
		folder = folder.replace("/","\\");
        plugins = Plugger.getPlugList(PluginsList.loadPluglist());
        arguments = args;
        Plugger.loadPlugins(plugins);
        Plugger.onSplashStart(plugins);
        splash = new SplashScreen();
        start();
        //Edit the plugin list at pluglist.xml (same directory as settings.xml)
    }
    
    public static void start(){
        String[] settings = SettingsIO.loadSettings();

        if (settings == null) {
            settings = new String[5];
            settings[0] = "Native";
            settings[1] = "Tabs";
            settings[2] = "Visible";
            settings[3] = "English";
            settings[4] = "Visible";
        }

        LangSupporter.activeLang = new English();
        
        if (!settings[3].equals("English")) {
            if (settings[3].equals("Portuguese (European)")) {
                LangSupporter.activeLang = new Portuguese();
            } else if (settings[3].equals("German")) {
                LangSupporter.activeLang = new German();
            } else if (settings[3].equals("German (Old)")) {
                LangSupporter.activeLang = new GermanOld();
            } else if (settings[3].equals("Russian")) {
                LangSupporter.activeLang = new Russian();
            } else if (settings[3].equals("Spanish")) {
                LangSupporter.activeLang = new Spanish();
            } else {
                utilities.addError(36);
            }
        }

        try {
            if (settings != null && settings[0] != null && settings[0].equals("Native")) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else if (settings == null || settings[0] == null || settings[0].equals("Cross-platform")) {
                javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.OceanTheme());
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } else if (settings[0].equals("Motif")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            } else if (settings[0].equals("Metal")) {
                javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.DefaultMetalTheme());
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            
        } catch (Exception e) {}
        Plugger.onMainWindowStart(plugins);
        ActorEditor.setupActions();
        
        window = new Aurwindow(settings);
        window.console.setText(output);
        Aurwindow.globalsettings = new org.gcreator.components.GlobalSettings();
        Aurwindow.newfilegroup = new NewFileGroup();
        Aurwindow.newproject = new NewProject();
        Aurwindow.about = new AboutPanel();
        if(splash!=null){
            Plugger.onSplashDispose(gcreator.plugins);
            window.menubar.updateUI();
            splash.dispose();
        }
        /*if(window!=null){
            SwingUtilities.updateComponentTreeUI(window.consolepopup);
            //SwingUtilities.updateComponentTreeUI(window); //NULLPOINTEREXCEPTION try...catch doesn't work.
        }*/
    }
}
