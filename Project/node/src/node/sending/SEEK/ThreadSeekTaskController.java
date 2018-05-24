/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.sending.SEEK;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.notImplementation.base.MCController;
import node.receiver.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class ThreadSeekTaskController extends MCController implements Runnable {

    private final int seekOrder;

    public ThreadSeekTaskController(MultiCastSender sender, int port) {
        super(null, sender);
        this.seekOrder = port - 6000;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            int port = seekOrder+6000;
            waitingSeconds(2*seekOrder);
            getSender().send(String.format("SEEK %d",port));
        } catch (IOException ex) {
            Logger.getLogger(ThreadSeekTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
