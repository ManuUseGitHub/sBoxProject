package Server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import serverutilities.ClientPresenceManager;

/**
 *
 * @author manI3
 */
public class SBoxProxyServer extends ThreadServerGroupTCP {

    private final Map<String, ClientPresenceManager> presencesManager;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public SBoxProxyServer(int port) {
        super(port);
        this.presencesManager = new HashMap<>();
    }

    @Override
    protected void onAcceptingNewone(Socket newClient, String who) {
        sendToClient(newClient, "OK Bienvenue parmis nous " + who);
        System.out.println(who + " is connected");

        synchronized (presencesManager) {
            Runnable remove = removePresenceOnClosed(who);
            //presencesManager.put(who, new ClientPresenceManager(newClient, remove));
        };
    }

    public void setActif(String who) {
        synchronized (presencesManager) {
            //presencesManager.get(who).reset();
        };
    }

    public void abbord(String who) {
        synchronized (presencesManager) {
            presencesManager.get(who).abbord();
        };
    }

    private Runnable removePresenceOnClosed(String who) {
        return () -> {
            synchronized (presencesManager) {
                presencesManager.remove(who);
            }
        };
    }

    @Override
    protected void atResponse(String who) {
        setActif(who);
    }

    @Override
    protected void atListeningSomething(String who) {
        setActif(who);
        System.out.println("something for who is up"+who);
    }
}
