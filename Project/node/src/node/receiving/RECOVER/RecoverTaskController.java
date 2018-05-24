/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.RECOVER;

import java.util.Observable;
import node.Constantes;
import node.notImplementation.base.MCController;
import node.receiver.MultiCastSender;
import node.receiver.ThreadMCReceiver;
import node.sending.RESTORE.RestoreSenderFromProjects;

/**
 *
 * @author MAZE2
 */
public class RecoverTaskController extends MCController {

    private final RestoreSenderFromProjects restoreSender;

    public RecoverTaskController(MultiCastSender sender) {
        super(new RecoverTaskAnalyser(), sender);
        this.restoreSender = new RestoreSenderFromProjects(sender);
    }

    @Override
    public void update(Observable o, Object args) {
        super.update(o, args);

        if (getAnalyseResult().equals("RETABLIR")) {
            if (o instanceof ThreadMCReceiver) {
                restoreSender.seekAndSendProjectRequest(Constantes.REPOSITORY_LOCATION);
            }

        }
    }
}
