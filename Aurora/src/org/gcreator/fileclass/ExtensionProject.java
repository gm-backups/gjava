/*
 * GameProjectr.java
 * 
 * Created on 5/Set/2007, 14:52:05
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.fileclass;

import org.gcreator.managers.FilesFinder;

/**
 *
 * @author Luís
 */
public class ExtensionProject extends Project {
    public ExtensionProject(String name, String location){
        super(name, location);
    }
    
    @Override
    public boolean allowsFileType(String format){
        return false;
    }
    
    @Override
    public boolean allowsGroup(Group group){
        return false;
    }
    
    @Override
    public String getType(){
        return "Extension";
    }
    
    public String getObjectType(){
        return "ExtensionProject";
    }
    
    @Override
    public Group newGroup(String name){
        Group group = new Group(this, name);
        add(group);
        return group;
    }
    
    public org.gcreator.fileclass.File[] getJavaFiles(){
        return org.gcreator.managers.FilesFinder.getFilesAt(this, "java");
    }
    
    public org.gcreator.fileclass.File[] getCppFiles(){
        return org.gcreator.managers.FilesFinder.getFilesAt(this, "cpp");
    }
}