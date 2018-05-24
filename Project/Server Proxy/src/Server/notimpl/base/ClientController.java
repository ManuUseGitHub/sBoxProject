/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.notimpl.base;

import Server.notimpl.InteractNetwork;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public abstract class ClientController extends Controller {

    private final InteractNetwork client;

    private final TaskAnalyser analyser;
    private String analyseResult;

    public ClientController(InteractNetwork client, TaskAnalyser analyser) {
        this.analyser = analyser;
        this.client = client;
    }

    public ClientController(InteractNetwork client) {
        this(client, null);
    }

    protected void waitingSeconds(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MCController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendResponse(String... message) {
        client.sendResponse(message[0]);
    }

    /**
     * @return the analyseResult
     */
    public String getAnalyseResult() {
        return analyseResult;
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        if (analyser != null) {
            analyseResult = analyser.analyse(getMessage());
        }
    }
}
