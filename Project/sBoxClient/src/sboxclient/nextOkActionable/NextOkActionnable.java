/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.nextOkActionable;

import java.util.Observable;
import java.util.Observer;
import sboxclient.Task;
import sboxclient.TaskAnalyser;
import sboxclient.server.ServerClient;

/**
 *
 * @author MAZE2
 */
public abstract class NextOkActionnable implements Observer {
    private final TaskAnalyser analyser;
    private final ServerClient client;

    public NextOkActionnable(ServerClient client) {
        this.analyser = new TaskAnalyser();
        this.client = client;
    }
    @Override
    public void update(Observable o, Object arg) {
        new Thread(() -> {
            if (o instanceof NextOkActionnableTask) {
                String message = ((Task) o).getMessage();
                String analize = analyser.analyse(message + " ");
                onGoodReceived(o, analize);
            }
        }).start();
    }

    public abstract void onGoodReceived(Observable o, String analize);

    ServerClient getClient() {
        return client;
    }
}
