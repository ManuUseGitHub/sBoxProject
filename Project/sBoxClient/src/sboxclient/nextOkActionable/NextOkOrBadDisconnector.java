/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.nextOkActionable;

import java.util.Observable;
import sboxclient.server.SBoxServerClient;
import sboxclient.server.ServerClient;

/**
 *
 * @author MAZE2
 */
public class NextOkOrBadDisconnector extends NextOkActionnable{

    public NextOkOrBadDisconnector(ServerClient client) {
        super(client);
    }
    
    
    @Override
    public void onGoodReceived(Observable o, String analize) {
        if ("GOOD".equals(analize)||"BAD".equals(analize)) {
            if (((NextOkActionnableTask) o).isNextOkActionnable()||"BAD".equals(analize)) { // error case quit directly
                ((SBoxServerClient)super.getClient()).sendToServer("QUIT");
            }
        }
    }
}
