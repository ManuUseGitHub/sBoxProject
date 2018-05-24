/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.sending.RECOVER;

import Server.notimpl.base.MCController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class ThreadRecoverTaskController extends MCController implements Runnable{

    public ThreadRecoverTaskController(MultiCastSender sender) {
        super(null,sender);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            getSender().send("RECOVER");
        } catch (IOException ex) {
            Logger.getLogger(ThreadRecoverTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
