/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.notimpl.base;

import node.INFO.ThreadInfoTaskController;
import node.MultiCastSender;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public abstract class MCController extends Controller {

    private final MultiCastSender sender;
    private final TaskAnalyser analyser;
    private String analyseResult;

    public MCController(TaskAnalyser analyser, MultiCastSender sender) {
        this.analyser = analyser;
        this.sender = sender;
    }

    protected void waitingSeconds(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadInfoTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the sender
     */
    public MultiCastSender getSender() {
        return sender;
    }

    protected void repondre(String message) {
        try {
            sender.send(message);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MCController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MCController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            analyseResult = analyser.analyse(super.getMessage());
        }
    }

}
