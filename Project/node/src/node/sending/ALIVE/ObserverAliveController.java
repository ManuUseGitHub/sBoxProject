/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.sending.ALIVE;

import java.util.logging.Level;
import java.util.logging.Logger;
import node.notImplementation.base.MCController;
import node.receiver.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class ObserverAliveController extends MCController implements Runnable {

    private final int id;

    public ObserverAliveController(MultiCastSender server, int id, String ipMc1) {
        super(null,server);

        this.id = id;

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            sendResponse("ALIVE " + id);

            try {
                Thread.sleep(15000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ObserverAliveController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
