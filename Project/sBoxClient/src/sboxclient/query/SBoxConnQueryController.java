/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.query;

import java.io.IOException;
import java.util.Observable;
import javafx.scene.control.Control;
import sboxclient.connexion.ConnexionController;
import sboxclient.server.SBoxServerClient;
import sboxclient.project.ProjectManager;
import serverutilities.ThreadingCountDown;
import serviceDefinition.ServiceDef;

/**
 *
 * @author MAZE2
 */
public class SBoxConnQueryController extends ConnexionController {

    private Control listeur;
    private final ThreadingCountDown countDown;
    private SBoxServerClient queryClient;
    private boolean isQuerying;

    public SBoxConnQueryController() {
        countDown = new ThreadingCountDown(5, 1000);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("QUERY")) {
            onQueryReceived();
        } else if (getClient() != null && arg.equals("QUIT")) {
            getClient().sendToServer("QUIT");
        }
    }

    public void setCibledComboboxForProjects(Control listeur) {
        this.listeur = listeur;
    }

    private void connect() {
        if (countDown.isCountdownUp()) {
            try {
                countDown.reset();
                queryClient = new SBoxServerClient(
                        Integer.parseInt(getTxtFPort().getText()),
                        getTxtFIpServer().getText(),
                        "Query" + getTxtFPseudo().getText());
                countDown.setEndAction(quitAfterTimeOut(), this.getClass());
                //System.err.println(countDown);
            } catch (IOException ex) {
                countDown.end();
                System.out.println(ex);
            }
            setClient(queryClient);
        }
    }

    @ServiceDef(desc = "la connexion se termine à la fin d'un timeout de 5 secondes",
            params = {})
    public Runnable quitAfterTimeOut() {
        return () -> {
            if (getClient() != null) {
                getClient().sendToServer("QUIT");
            }
            setClient(null);
        };
    }

    private void onQueryReceived() {
        new Thread(() -> {
            if (!isQuerying) {
                isQuerying = true;
                sleep(1000);
                if (queryClient != null && !countDown.isCountdownUp()) {
                    countDown.reset();
                    queryClient.setTask(new QueryTask());
                    queryClient.sendToServer("QUERY");
                    ProjectManager manager = new ProjectManager(listeur);
                    queryClient.addObserverOnTask(manager);
                    isQuerying = false;
                    return;
                }

                connect();
                isQuerying = false;
                update(null, "QUERY");
                
            }
        }).start();
    }
}
