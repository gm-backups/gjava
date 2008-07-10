/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (c) 2008 BobSerge or Bobistaken <serge_1994@hotmail.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.exceptions;

/**
 *
 * @author Luís
 */
public class InvalidSignalException extends Exception{
    Object signal;
    public InvalidSignalException(Object signal){
        this.signal = signal;
    }
    
     
    public String toString(){
        return "InvalidSignalException with signal " + signal.toString();
    }
}
