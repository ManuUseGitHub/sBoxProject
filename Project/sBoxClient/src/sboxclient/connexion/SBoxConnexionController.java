/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.connexion;

import sboxclient.server.SBoxServerClient;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import javafx.scene.layout.BorderPane;
import sboxclient.SubController;

/**
 *
 * @author MAZE2
 */
public class SBoxConnexionController extends ConnexionController {

    private final ConnexionMessenger messenger;
    private List<SubController> controllers;
    private BorderPane borderPaneTab;
    private BorderPane borderPaneConnexion;

    public SBoxConnexionController(ConnexionMessenger connexionMessenger) {
        this.messenger = connexionMessenger;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("CONNECT")) {
            String ip = getTxtFIpServer().getText();
            int port = 0;

            ConnexionTask task = new ConnexionTask();
            try {
                port = Integer.parseInt(getTxtFPort().getText());
                connect(task, port, ip);

            } catch (IOException e) {
                task.setMessage("ERR Impossible de se connecter");
                System.err.println(e.toString());
            }
        }
    }

    public void setPanes(BorderPane BorderPaneConnexion, BorderPane BorderPaneTab) {
        this.borderPaneConnexion = BorderPaneConnexion;
        this.borderPaneTab = BorderPaneTab;
    }

    /**
     * @param controllers the controllers to set
     */
    public void setControllers(List<SubController> controllers) {
        this.controllers = controllers;
    }

    private void switchScenes() {
        new Thread(() -> {
            if (getClient() == null) {
                sleep(2000);
                borderPaneConnexion.setVisible(false);
                borderPaneTab.setVisible(true);
            }
        }).start();

    }

    private void connect(ConnexionTask feedBack, int port, String ip) throws IOException {
        switchScenes();
        feedBack.addObserver(messenger);
        SBoxServerClient client = new SBoxServerClient(port, ip, getTxtFPseudo().getText());
        client.setTask(feedBack);

        for (SubController sub : controllers) {
            sub.setClient(client);
        }
    }
}
