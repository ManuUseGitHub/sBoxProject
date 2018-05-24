/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.WHOHAS;

import Server.Constantes;
import Server.notimpl.base.MCController;
import node.MultiCastSender;
import util.Parser;
import java.io.File;
import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public class WhohasController extends MCController {

    private final Parser parser;
    private final int port;

    public WhohasController(MultiCastSender sender, int port) {
        super(new WhohasTaskAnalyser(), sender);
        this.port = port;
        parser = new Parser();
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);

        if (getAnalyseResult().equals("QUI_A")) {
            provideOrNot(getMessage());
        }
    }

    private void provideOrNot(String message) {

        String[] parsed = parser.parseMultipleGroups(message,WhohasTaskAnalyser.WHOHAS);
        String who = parsed[0], id = parsed[1], version = parsed[2];
        String filepath = String.format("%s/%s/%s %s.zip", Constantes.REPOSITORIES_LOCATION, who, id, version);

        if (new File(filepath).exists()) {
            repondre(String.format("PROVIDE %s %s %s %d", who, id, version, port));
        }
    }

}
