/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.received.WHOHAS;

import java.io.File;
import java.util.Observable;
import node.TaskParser;
import node.notImplementation.base.MCController;
import node.notImplementation.base.TaskAnalyser;
import node.Constantes;
import node.receiver.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class WhohasController extends MCController {

    private final TaskParser parser;
    private final int port;

    private final MultiCastSender sender;
    
    public WhohasController(MultiCastSender sender, int port) {
        super(new WhohasTaskAnalyser(),sender);
        this.port = port;
        this.sender = sender;
        parser = new TaskParser();
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        if (getAnalyseResult().equals("QUI_A")) {
            provideOrNot(getMessage());
        }
    }

    private void provideOrNot(String message) {

        String[] parsed = parser.parseMultipleGroups(message, WhohasTaskAnalyser.WHOHAS);
        String who = parsed[0], id = parsed[1], version = parsed[2];
        String filepath = String.format("%s/%s/%s %s.zip", Constantes.REPOSITORY_LOCATION, who, id, version);

        if (new File(filepath).exists()) {
            sendResponse(String.format("PROVIDE %s %s %s %d", who, id, version, port));
        }
    }
}
