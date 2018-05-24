/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.notimpl.base;

import java.util.Observable;
import Server.notimpl.InteractNetwork;

/**
 *
 * @author MAZE2
 */
public abstract class ServerTCPController extends Controller{

    private final TaskAnalyser analyser;
    private final InteractNetwork server;
    private String analyseResult;

    public ServerTCPController(InteractNetwork server, TaskAnalyser analyser) {
        this.server = server;
        this.analyser = analyser;
    }

    public void analyse(String message){
        analyseResult = analyser.analyse(message);
    }
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        analyse(getMessage());
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
        getServer().sendResponse(argsMessage);
    }

    /**
     * @return the server
     */
    public Server getServer() {
        return (Server)server;
    }
}
