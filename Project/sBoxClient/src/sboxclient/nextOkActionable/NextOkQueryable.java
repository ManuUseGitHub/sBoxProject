/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.nextOkActionable;

import java.util.Observable;
import java.util.Observer;
import sboxclient.server.ServerClient;

/**
 *
 * @author MAZE2
 */
public class NextOkQueryable extends NextOkActionnable {

    private final Observer queryObserver;

    public NextOkQueryable(ServerClient client, Observer queryObserver) {
        super(client);
        this.queryObserver = queryObserver;
    }

    @Override
    public void onGoodReceived(Observable o, String analize) {
        if ("GOOD".equals(analize)) {
            queryObserver.update(null, "QUERY");
        }
    }
}
