package org.dolphin.game.api.types;

public class Queue extends Variable {
java.util.LinkedList l;
    
    public Queue(){
        l = new java.util.LinkedList();
    }
    
    public void destroy()
    {
        l=null;
    }
    
       public void clear()
    {
        l.clear();
    }
     
     public int size()
    {
      return  l.size();
    } 
     
     public boolean empty()
    {
      return  l.isEmpty();
    } 
     
     
             
             public boolean enqueue(Object o)
    {
      return  l.add(o);
    } 
             
              public Variable dequeue()
    {
      return (Variable) l.poll();
    } 
              
               public Variable head()
    {
      return (Variable) l.getLast();
    } 
               
                public Variable tail()
    {
      return (Variable) l.getFirst();
    } 
}
