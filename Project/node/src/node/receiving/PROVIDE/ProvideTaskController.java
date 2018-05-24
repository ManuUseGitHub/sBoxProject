package node.receiving.PROVIDE;

import node.receiving.RETRIEVE.for_storage.RetrieveTaskController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import node.ThreadingLightClient;
import node.environnement.SingletonWhohasList;
import node.notImplementation.base.MCController;
import node.receiver.MultiCastSender;
import node.receiver.ThreadMCReceiver;

/**
 *
 * @author MAZE2
 */
public class ProvideTaskController extends MCController {

    private final ProvideTaskParser parser;
    private final Map<Integer, ThreadingLightClient> whoIsServed;
    private final SingletonWhohasList providingList = SingletonWhohasList.getINSTANTCE();
    private final int localPort;
    private boolean isRetrieveing;

    public ProvideTaskController(MultiCastSender sender, int localPort) {
        super(new ProvideTaskAnalyser(), sender);
        parser = new ProvideTaskParser();
        whoIsServed = Collections.synchronizedMap(new HashMap<Integer, ThreadingLightClient>());
        this.localPort = localPort;
    }

    @Override
    public void update(Observable o, Object args) {
        super.update(o, args);
        String msg = getMessage();

        if (getAnalyseResult().equals("GERE")) {
            if (o instanceof ThreadMCReceiver) {
                String[] parsed = parser.parseProvide(msg);
                synchronized (whoIsServed) {
                    handleRetrieveMissingProject(parsed, (ThreadMCReceiver) o);
                }
            }
        }
    }

    private void handleRetrieveMissingProject(String[] parsed, ThreadMCReceiver observable) {

        int port = Integer.parseInt(parsed[3]);

        if (isWaitingForProvide(parsed) && !isRetrieveing && localPort != port) {   // 1 traitement par port
            try {
                if (!whoIsServed.containsKey(port)) {
                    isRetrieveing = true;

                    Thread.sleep(1000); // on attend 2 secondes pour être sûr que la connexion soit faite

                    ThreadingLightClient client = new ThreadingLightClient(port, observable.getIpFrom());
                    whoIsServed.put(port, client);
                    
                    Thread.sleep(2000); // on attend 2 secondes pour être sûr que la connexion soit faite
                    
                    client.addObserver(new RetrieveTaskController(client, parsed));
                    retrieving(observable.getIpFrom(), port, parsed);
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    private String getWhohasTask(String[] parsed) {
        String who = parsed[0], id = parsed[1], version = parsed[2];
        return String.format("WHOHAS %s %s %s", who, id, version);
    }

    private boolean isWaitingForProvide(String[] parsed) {
        String who = parsed[0], id = parsed[1], version = parsed[2];
        String whohasTask = String.format("WHOHAS %s %s %s", who, id, version);
        return providingList.isHandling(whohasTask);
    }

    private Observer Remover(int port, String whohawtask) {
        return (Observable o, Object arg) -> {
            if (o instanceof ThreadingLightClient) {
                ThreadingLightClient client = (ThreadingLightClient) o;
                if (client.getMessage() != null && client.getMessage().matches("^(?i)(quit)$")) {
                    synchronized (whoIsServed) {
                        whoIsServed.get(port).deleteObservers();
                        whoIsServed.remove(port);
                        providingList.deleteWhohasTask(whohawtask);
                        isRetrieveing = false;
                    }
                }
            }
        };
    }

    private void retrieving(String ipFrom, int port, String[] parsed) {
        String who = parsed[0], id = parsed[1], version = parsed[2];
        System.out.println(String.format("(from %s %d) >> Providing for %s %s", ipFrom, port, id, version));

        ThreadingLightClient client = whoIsServed.get(port);
        client.addObserver(Remover(port, getWhohasTask(parsed)));

        String retrieveTask = String.format("RETRIEVE %s %s %s", who, id, version);
        client.sendResponse(retrieveTask);
        System.out.println(String.format("(to %s %d) << %s", ipFrom, port, retrieveTask));
    }
}
