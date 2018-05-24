/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.project;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import sboxclient.QuerryTaskAnalyser;
import sboxclient.Task;

/**
 *
 * @author MAZE2
 */
public class ProjectManager implements Observer {

    private final Control lister;
    private final QuerryTaskAnalyser analyser;

    public ProjectManager(Control lister) {
        this.lister = lister;
        analyser = new QuerryTaskAnalyser();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Task) {
            String message = ((Task) o).getMessage();
            if (analyser.analyse(message).equals("DEMANDE")) {
                String[] list = ProjectParser.parseProjects(message);
                modifyList(list);
            }
        }
    }

    private void modifyList(String[] list) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (lister instanceof ComboBox) {
                    ((ComboBox) lister).setItems(FXCollections.observableArrayList(Arrays.asList(list)));
                } else if (lister instanceof ListView) {
                    ((ListView) lister).setItems(FXCollections.observableArrayList(Arrays.asList(list)));
                }
            }
        });
    }
}
