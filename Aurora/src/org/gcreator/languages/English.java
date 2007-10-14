/*
 * English.java
 * 
 * Created on 4/Set/2007, 11:22:39
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.languages;

import org.gcreator.units.Dictionary;
import components.*;
import org.gcreator.exceptions.*;

/**
 *
 * @author Luís
 */
public class English extends Dictionary{
    public English(){
        status = "Finished";
        language = "English";
        authors = new String[10];
        authors[0] = "Luís Reis";
        //If you changed this language, add your name here.
        entry = new String[200];
        entry[0] = "File";
        entry[1] = "Edit";
        entry[2] = "View";
        entry[3] = "Build&Run";
        entry[4] = "Help";
        entry[5] = "New Project";
        entry[6] = "New File/Group";
        entry[7] = "Open Project";
        entry[8] = "Save Project";
        entry[9] = "Save Project As";
        entry[10] = "Import...";
        entry[11] = "Export...";
        entry[12] = "Close Project";
        entry[13] = "Exit";
        entry[14] = "Clear Console";
        entry[15] = "Look&Feel";
        entry[16] = "Display mode";
        entry[17] = "Native look";
        entry[18] = "Cross-platform look";
        entry[19] = "Motif look";
        entry[20] = "Tabs (Top)";
        entry[21] = "MDI";
        entry[22] = "Display output box";
        entry[23] = "Select language";
        entry[24] = "About";
        entry[25] = "Help"; //The menu item, not the menu itself
        entry[26] = "Welcome!"; //Tab name
        entry[27] = "Help!"; //Tab name
        entry[28] = "Select language"; //Tab name
        entry[29] = "Finished loading application.";
        entry[30] = "Error creating settings file";
        entry[31] = "Error writing settings file";
        entry[32] = "Error reading settings file";
        entry[33] = "Bad settings file structure";
        entry[34] = "Doubled setting node";
        entry[35] = "Missing setting property";
        entry[36] = "Language unavailable";
        entry[37] = "Welcome to Aurora,";
        entry[38] = "the next generation of G-Creator.";
        //Toolbar
	entry[39] = "New Project"; 
	entry[40] = "Open"; 
	entry[41] = "Save"; 
	entry[42] = "Save As"; 
	entry[43] = "Add Sprite";
	entry[44] = "Add Sound"; 
	entry[45] = "Add Actor";
	entry[46] = "Add Scene";
        //Language menu
        entry[47] = "Language";
        entry[48] = "Status";
        entry[49] = "Apply Language";
        entry[50] = "Restart the application to apply the changes";
        //Tree
        entry[51] = "Workspace";
        //Toolbar
        entry[52] = "Add Class";
        //Menu
        entry[53] = "Save All Projects";
        //New Project Wizard
        entry[54] = "Choose the type of your project";
        entry[55] = "New Project";
        entry[56] = "Games";
        entry[57] = "Packages";
        entry[58] = "Empty Game";
        entry[59] = "Empty Package";
        entry[60] = "Project name";
        entry[61] = "Organized Package";
        entry[62] = "Extensions";
        entry[63] = "Empty Extension";
        entry[64] = "Graphics";
        entry[65] = "Start in full-screen mode";
        entry[66] = "Allow the player to resize the room window";
        entry[67] = "Don't draw a border in windowed mode";
        entry[68] = "Don't show the buttons in the window caption";
        entry[69] = "Display mouse";
        entry[70] = "Display FPS in window caption";
        entry[71] = "Resolution";
        entry[72] = "Set the resolution of the screen";
        entry[73] = "Colour Depth";
        entry[74] = "No change";
        //View
        entry[75] = "View toolbar";
        //Find
        entry[76] = "Find"; //Title
        entry[77] = "Find"; //Label
        entry[78] = "Text to find";
        entry[79] = "Find"; //Button
        entry[80] = "Case-Sensitive"; 
        entry[81] = "Use Regular expressions";
        entry[82] = "Treat \\n as line break";
        //Find&Replace
        entry[83] = "Find&Replace"; //Title
        entry[84] = "Replace"; //Label
        entry[85] = "by";
        entry[86] = "Replace"; //Button
        entry[87] = "Replace All";
        //More menu items
        entry[88] = "Save file";
        entry[89] = "Save all files";
        entry[90] = "Tabs (Left)";
        entry[91] = "Tabs (Bottom)";
        entry[92] = "Tabs (Right)";
        entry[114] = "Tools";
        entry[93] = "Update";
        entry[94] = "Create";
        //And the new File/Group
        entry[95] = "New...";
        entry[96] = "New File or Group";
        entry[97] = "Standard items";
        entry[98] = "Set as main project";
        entry[99] = "Clean current project";
        entry[100] = "Clean main project";
        entry[101] = "Build current project";
        entry[102] = "Test current project";
        entry[103] = "Build&Test current project";
        entry[104] = "Final Build current project";
        entry[105] = "Build main project";
        entry[106] = "Test main project";
        entry[107] = "Build&Test main project";
        entry[108] = "Final Build main project";
        //Extensions manager
        entry[109] = "Extensions manager";
        entry[110] = "Extensions manager"; //Title
        entry[111] = "Add";
        entry[112] = "Remove";
        entry[113] = "Revert changes";
        //Popup
        //114 is already used
        entry[115] = "Copy message";
        entry[116] = "Hide console";
        entry[117] = "Hide toolbar";
        entry[118] = "Import image";
        entry[119] = "Zoom";
        //Settings editor again
        entry[120] = "Frequency";
        entry[121] = "Other";
        entry[122] = "Default Keys";
        entry[123] = "Let <ESC> end the game";
        entry[124] = "Let <F4> switch between screen modes";
        entry[125] = "Let <F5> save the game and <F6> load it.";
        entry[126] = "Loading";
        entry[127] = "Show image while loading";
        entry[128] = "Image:";
        entry[129] = "Icon:";
        //Options
        entry[130] = "Global settings";
        entry[131] = "Global settings"; //Title
        //---
        entry[132] = "Metal theme";
        
        
        try{
            init();
        }
        catch(Exception e){} // To ignore
    }
    
    @Override
    public String getSpecialEntry(String value){
        if(value.equals("GPlus.CompileCur"))
            return "Compile Current Extension";
        if(value.equals("GAH.Menu"))
            return "Advanced";
        if(value.equals("GAH.GC"))
            return "Force garbage collection";
        return "";
    }
}