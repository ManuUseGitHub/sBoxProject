/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.environnement;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.receiver.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class SingletonWhohasList {

    private final Set<String> infoprovideList;

    private static SingletonWhohasList INSTANTCE = new SingletonWhohasList();
    private MultiCastSender sender;

    private SingletonWhohasList() {
        infoprovideList = (Set<String>) Collections.synchronizedSet(new HashSet<String>());
    }

    public static SingletonWhohasList getINSTANTCE() {
        return INSTANTCE;
    }

    private Runnable handleWhohas() {
        return () -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                    synchronized (infoprovideList) {
                        if (0 < infoprovideList.size()) {
                            String next = infoprovideList.iterator().next();
                            sender.send(next);
                        }else{
                            break;
                        }
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(SingletonWhohasList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

    }

    public void addWhohasTask(String who, String id, String version) {
        synchronized (infoprovideList) {
            String request = String.format("WHOHAS %s %s %s", who, id, version);
            infoprovideList.add(request);
        }
        new Thread(handleWhohas()).start();
    }

    public void deleteWhohasTask(String infoTask) {
        synchronized (infoprovideList) {
            infoprovideList.remove(infoTask);
        }
    }

    public boolean isHandling(String whohas) {
        synchronized (infoprovideList) {
            return infoprovideList.contains(whohas);
        }
    }

    public void setSender(MultiCastSender sender) {
        this.sender = sender;
    }
}
