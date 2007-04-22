package org.gjava.actoreditor.imageformat;

import org.gjava.actoreditor.imageformat.ImageDataObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class OpenImage extends CookieAction {
    
    protected void performAction(Node[] activatedNodes) {
        try     {
            org.gjava.actoreditor.imageformat.ImageDataObject imageDataObject = (org.gjava.actoreditor.imageformat.ImageDataObject) activatedNodes[0].getLookup().lookup(org.gjava.actoreditor.imageformat.ImageDataObject.class);
            ij.OpenImage i = new ij.OpenImage();

            i.openij(imageDataObject.getPrimaryFile().getURL().getFile().replaceAll("%20", " "));
        }
        catch (FileStateInvalidException ex) {
            Exceptions.printStackTrace(ex);
        }
}
    
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }
    
    public String getName() {
        return NbBundle.getMessage(OpenImage.class, "CTL_SomeAction");
    }
    
    protected Class[] cookieClasses() {
        return new Class[] {
            ImageDataObject.class
        };
    }
    
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
