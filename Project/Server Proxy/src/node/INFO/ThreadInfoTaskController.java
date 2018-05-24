package node.INFO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Server.Constantes;
import Server.notimpl.base.MCController;
import node.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class ThreadInfoTaskController extends MCController implements Runnable {
    private final InfoSenderFromProjects infosender;
    
    public ThreadInfoTaskController(MultiCastSender sender) {
        super(null, sender);
        this.infosender = new InfoSenderFromProjects(sender);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            infosender.seekAndSendInfo(Constantes.REPOSITORIES_LOCATION);
            waitingSeconds(30);
        }
    }

    
}
