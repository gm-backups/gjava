package org.gjava.actoreditor;

import java.io.BufferedReader;
import org.gjava.actoreditor.Action.ActionData;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.text.DataEditorSupport;
import org.openide.util.Utilities;

public class actorDataObject extends MultiDataObject 
implements Lookup.Provider {
    
   public  actorData data = new actorData();
    
    public final FileObject pf2;

    public actorDataObject(FileObject pf, actorDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        CookieSet cookies = getCookieSet();
        pf2 = pf;
        InputStream input = pf.getInputStream();
        data.name = pf.getName();
        System.out.println(pf.getPath());
    BufferedReader from=new BufferedReader(new InputStreamReader(input));
   
        String line;
        while ((line=from.readLine()) != null)
        {
            if (line.contains("<Image>") && line.contains("</Image>"))
            {
              data.img = line.replaceAll("<Image>", "").replaceAll("</Image>", "");
               System.out.println(data.img);
            }
        }
    input.close();
       // data = new actorData();
       /* cookies.add(new OpenCookie() {
        
          public void open() {
        
        System.out.println("hey,hey,hey,hey");
         // c = (actorDataObject) node.getCookie(actorDataObject.class);
      
        //ActorEditorTopComponent re = ActorEditorTopComponent.getInstance(pf2.getPath());
        //re.setName(pf2.getPath());
       // re.path = pf2.getPath();
        //re.setHtmlDisplayName(c.getName());
       // re.setIcon(Utilities.loadImage("org/gjava/actoreditor/object.png"));
        //re.open();
       // re.requestActive();
    }
            
        });
        */cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
    }
    
    protected Node createNodeDelegate() {
        return new actorDataNode(this, data);
    }

    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }
}
