package Server.notimpl.base;

import Server.notimpl.InteractNetwork;
import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public abstract class ServerTCPGroupController extends ServerTCPController {

    private String who;
    
    public ServerTCPGroupController(InteractNetwork server,TaskAnalyser analyser) {
        super(server,analyser);
    }

    protected String getWho(){
        return who;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        who = ((String[]) arg)[1];
    }
    
}
