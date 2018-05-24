/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sboxclient.main.FXMLMainWindowController;
/**
 * hbjhvkb
 *
 * @author tonioush
 */
public class SBoxClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //commentaire qui ne sert a rien
        System.setProperty("glass.accessible.force", "false");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sboxclient/main/FXMLMainWindow.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent we) -> {            
            FXMLMainWindowController controller = (FXMLMainWindowController) loader.getController();
            controller.quitter();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
