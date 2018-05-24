/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.connexion;

import javafx.scene.control.TextField;
import sboxclient.SubController;

/**
 *
 * @author MAZE2
 */
public abstract class ConnexionController extends SubController{

    private TextField txtFPort;
    private TextField txtFIpServer;
    private TextField txtFPseudo;
    
    public void setInputs(TextField lblPort, TextField lblIpServeur, TextField txtFPseudo) {
        this.txtFPort = lblPort;
        this.txtFIpServer = lblIpServeur;
        this.txtFPseudo = txtFPseudo;
    }
    
    public void setInputs(TextField lblPort, TextField lblIpServeur) {
        this.txtFPort = lblPort;
        this.txtFIpServer = lblIpServeur;
    }

    /**
     * @return the txtFPort
     */
    public TextField getTxtFPort() {
        return txtFPort;
    }

    /**
     * @return the txtFIpServer
     */
    public TextField getTxtFIpServer() {
        return txtFIpServer;
    }

    /**
     * @return the txtFPseudo
     */
    public TextField getTxtFPseudo() {
        return txtFPseudo;
    }
}
