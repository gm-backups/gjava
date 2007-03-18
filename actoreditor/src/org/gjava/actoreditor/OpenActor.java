package org.gjava.actoreditor;


import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.CookieAction;

public class OpenActor extends CookieAction {
    
   public static Node[] node = new Node[1];
    
    protected void performAction(Node[] activatedNodes) {
       
       actorDataObject c = (actorDataObject) activatedNodes[0].getCookie(actorDataObject.class);
      
        ActorEditorTopComponent re = ActorEditorTopComponent.getInstance(c.getName());
        re.setName(c.getName());
        re.setHtmlDisplayName(c.getName());
        re.setIcon(Utilities.loadImage("org/gjava/actoreditor/object.png"));
        re.open();
        re.requestActive();

    }
    
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }
    
    public String getName() {
        this.setEnabled(true);
        return NbBundle.getMessage(OpenActor.class, "CTL_OpenActor");
    }
    
    protected Class[] cookieClasses() {
        return new Class[] {
            actorDataObject.class
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
    
    public void open() {
        performAction(node);
    }

}
