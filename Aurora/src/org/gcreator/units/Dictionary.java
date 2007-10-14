/*
 * Dictionary.java
 * 
 * Created on 4/Set/2007, 11:16:25
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.units;

import org.gcreator.exceptions.*;

/**
 *
 * @author Luís
 */
public class Dictionary {
    protected String[] entry = null;
    protected String[] authors = null;
    protected String status = null;
    protected String language = null;
    
    public Dictionary(){}
    
    protected void init() throws NoLanguageNameException, NullDictionaryException{
        if(language==null)
            throw new NoLanguageNameException();
        if(status==null)
            status = "";
        if(entry==null)
            throw new NullDictionaryException();
    }
    
    public String getEntry(int num){
        if(num==-1)
            return "";
        if(num>=entry.length)
            return "";
        if(entry[num]==null)
            return "";
        return entry[num];
    }
    
    public String getLanguage() throws NoLanguageNameException{
        if(language==null)
            throw new NoLanguageNameException();
        return language;
    }
    
    public String[] getAuthors(){
        return authors;
    }
    
    public String getStatus(){
        return status;
    }
    
    public String getSpecialEntry(String value){
        return "";
    }
}