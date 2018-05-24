/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.notImplementation.base;

import java.util.Observable;
import java.util.Observer;
import node.notImplementation.InteractNetwork;
import node.notImplementation.Network;

/**
 *
 * @author MAZE2
 */
public abstract class ServerTCPController extends Controller {

    private final TaskAnalyser analyser;
    private final InteractNetwork server;
    private String analyseResult;

    public ServerTCPController(InteractNetwork server, TaskAnalyser analyser) {
        this.server = server;
        this.analyser = analyser;
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        analyseResult = analyser.analyse(getMessage());
    }

    /**
     * @return the analyseResult
     */
    public String getAnalyseResult() {
        return analyseResult;
    }

    /**
     * envoit un message au(x) client(s), le premier argument devrait toujours
     * être le message, les arguments suivent devraient être des compléments du
     * message. Ces compéments serviraient, par exemple, à identifier un socket
     * en multi connexion TCP
     * <b>Exemple d'appel:</b><br/>
     * respond("HELLO server ok","Frederick");
     *
     * @param argsMessage
     */
    protected void respond(String... argsMessage) {
        server.sendResponse(argsMessage);
    }
}
