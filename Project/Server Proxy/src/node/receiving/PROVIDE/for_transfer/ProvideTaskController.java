/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.PROVIDE.for_transfer;

import util.Parser;
import Server.ThreadMCReceiver;
import Server.notimpl.base.MCController;
import Server.pull.EndPullProcess;
import Stepping.Stepper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import node.MultiCastSender;
import node.TCP.RETRIEVE.ThreadingLightClient;
import node.environnement.SingletonWhohasList;
import Server.pull.PulledTaskController;
import node.sending.RETRIEVE.RetrieveForTransferTaskController;

/**
 *
 * @author MAZE2
 */
public class ProvideTaskController extends MCController {

    private final Parser parser;
    private final Map<Integer, ThreadingLightClient> whoIsServed;
    private final SingletonWhohasList providingList = SingletonWhohasList.getINSTANTCE();
    private final int localPort;
    private boolean isRetrieveing;
    private final Stepper otherStepper;


    public ProvideTaskController(MultiCastSender sender, int localPort, Stepper other) {
        super(new ProvideTaskAnalyser(), sender);
        parser = new Parser();
        whoIsServed = Collections.synchronizedMap(new HashMap<Integer, ThreadingLightClient>());
        this.localPort = localPort;
        otherStepper = other;
    }

    @Override
    public void update(Observable o, Object args) {
        super.update(o, args);
        String msg = getMessage();

        if (getAnalyseResult().equals("GERE")) {
            if (o instanceof ThreadMCReceiver) {
                String[] parsed = parser.parseMultipleGroups(msg, ProvideTaskAnalyser.PROVIDE);
                synchronized (whoIsServed) {
                    handleRetrieveMissingProject(parsed, (ThreadMCReceiver) o);
                }
            }
        }
    }

    private void handleRetrieveMissingProject(String[] parsed, ThreadMCReceiver observable) {

        int port = Integer.parseInt(parsed[3]);

        boolean isHandling = isWaitingForProvide(parsed);
        if (isHandling && !isRetrieveing && localPort != port) {   // 1 traitement par port
            try {
                isRetrieveing = true;

                Thread.sleep(1000); // on attend 2 secondes pour être sûr que la connexion soit faite

                ThreadingLightClient client = new ThreadingLightClient(port, observable.getIpFrom());
                whoIsServed.put(port, client);
                
                Thread.sleep(2000); // on attend 2 secondes pour être sûr que la connexion soit faite
                
                retrieving(observable.getIpFrom(), port, parsed);

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

    private Observer Remover(int port, String whohastask) {
        return (Observable o, Object arg) -> {
            if (o instanceof ThreadingLightClient) {
                ThreadingLightClient client = (ThreadingLightClient) o;
                if (client.getMessage() != null && client.getMessage().matches("^(?i)(quit)$")) {
                    synchronized (whoIsServed) {
                        whoIsServed.get(port).deleteObservers();
                        whoIsServed.remove(port);
                        providingList.deleteWhohasTask(whohastask);
                        isRetrieveing = false;
                    }
                }
            }
        };
    }

    private void retrieving(String ipFrom, int port, String[] parsed) {
        String who = parsed[0], id = parsed[1], version = parsed[2];

        providingList.addWhohasTask(who, id, version);

        ThreadingLightClient client = whoIsServed.get(port);
        RetrieveForTransferTaskController retriever = new RetrieveForTransferTaskController(getSender(),client, parsed,otherStepper);
        client.addObserver(retriever);
        System.out.println(String.format("(from %s %d) >> Providing for %s %s", ipFrom, port, id, version));

        client.addObserver(Remover(port, getWhohasTask(parsed)));

        String retrieveTask = String.format("RETRIEVE %s %s %s", who, id, version);
        client.sendResponse(retrieveTask);
        System.out.println(String.format("(to %s %d) << %s", ipFrom, port, retrieveTask));
    }
}
