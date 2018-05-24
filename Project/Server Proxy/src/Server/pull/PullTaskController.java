package Server.pull;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Server.notimpl.base.Server;
import Server.notimpl.base.ServerTCPGroupController;
import util.Parser;
import node.MultiCastSender;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.environnement.SingletonWhohasList;

/**
 *
 * @author MAZE2
 */
public class PullTaskController extends ServerTCPGroupController {

    private final Parser parser;
    private final MultiCastSender sender;
    

    public PullTaskController(Server server, MultiCastSender sender) {
        super(server, new PullTaskAnalyser());
        this.sender = sender;
        parser = new Parser();
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (!(obs instanceof EndPullProcess)) {
            super.update(obs, arg);

            String who = getWho();
            String message = getMessage();
            String analysed = getAnalyseResult();

            switch (analysed) {
                case "PULL":
                    sendWhoHas(who, message);
                    break;
                case "PULL_ID_ERROR":
                    respond("ERR C1 identifiant invalide", who);
                    break;
                case "PULL_VERSION_ERROR":
                    respond("ERR C1 version invalide", who);
                    break;
            }
        }
    }

    private void sendWhoHas(String who, String message) {
        try {
            String[] result = parser.parseMultipleGroups(message, PullTaskAnalyser.PULL);
            String id = result[0], version = result[1];

            String whohasTask = String.format("WHOHAS %s %s %s", who, id, version);
            SingletonWhohasList.getINSTANTCE().addWhohasTask(whohasTask);
            System.out.println(String.format("(to Other) << %s", whohasTask));
            sender.send(whohasTask);

        } catch (IOException ex) {
            Logger.getLogger(PullTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
