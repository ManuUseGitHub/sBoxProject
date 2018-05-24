/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.server;

import java.io.IOException;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import sboxclient.Task;

/**
 *
 * @author tonioush
 */
public class SBoxServerClient extends ServerClientImpl {

    private Task actualTask;

    public SBoxServerClient(int port, String ip, String who) throws IOException {
        super(port, ip, who);
    }

    @Override
    public void onHandleError(SocketException ex) {
        actualTask.setMessage(ex.getMessage());
    }

    @Override
    public void onReadingSomething() {
        setSentence(getSentence().trim());
        if (!getSentence().isEmpty() && actualTask != null) {
            actualTask.setMessage(getSentence());
        }
    }

    public void setTask(Task task) {
        if (this.actualTask != null) {
            this.actualTask.deleteObservers();  // si on actualTask n'est pas supprimé les Observers associés garde 
            // le liens vers l'objet non référencé l'objet pointé ne peut pas être garbage collecté donc réagactions aux événements 
            actualTask = null;
        }
        this.actualTask = task;
        addWatcher();
    }

    public void addObserverOnTask(Observer manager) {
        actualTask.addObserver(manager);
    }

    private void addWatcher() {
        actualTask.addObserver(
                new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                //System.out.println(o.toString() + " =>" + ((Task) o).getMessage());
                //TODO: Put here code to check all messages get by the task (on reading from server)
            }
        });
    }

    public Task getTask() {
        return actualTask;
    }
}
