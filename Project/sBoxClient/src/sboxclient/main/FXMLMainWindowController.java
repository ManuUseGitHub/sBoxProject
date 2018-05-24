/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.main;

import sboxclient.SubController;
import sboxclient.query.SBoxConnQueryController;
import sboxclient.connexion.SBoxConnexionController;
import Stepping.view.StepObserver;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import sboxclient.commit.CommitController;
import sboxclient.create.CreateController;
import sboxclient.pull.PullController;
import sboxclient.server.SBoxServerClient;
import sboxclient.project.ProjectParser;
import sboxclient.commit.CommitMessenger;
import sboxclient.connexion.ConnexionMessenger;
import sboxclient.create.CreateMessenger;
import sboxclient.nextOkActionable.NextOkActionnableTask;
import sboxclient.nextOkActionable.NextOkOrBadDisconnector;
import sboxclient.nextOkActionable.NextOkQueryable;
import sboxclient.pull.PullMessenger;

/**
 * FXML Controller class
 *
 * @author tonioush
 */
public class FXMLMainWindowController extends Observable implements Initializable {

    private SBoxServerClient client;
    private Map<String, SubController> controllers;

    //<editor-fold defaultstate="collapsed" desc="View part">
    @FXML
    private TabPane tabPane;
    @FXML
    private Pane paneConnection;
    @FXML
    private TextField txtFIpServeur;
    @FXML
    private TextField txtFPort;
    @FXML
    private Label lblFeedBackConnection;
    @FXML
    private TextField txtFPseudo;
    @FXML
    private TextField txtFieldCommitId;
    @FXML
    private TextField txtFieldPullId;
    @FXML
    private Font x1;
    @FXML
    private Font x2;
    @FXML
    private ProgressBar progress;
    private Observer obs;
    @FXML
    private Label progressPourcentage;
    @FXML
    private Label progressText;
    @FXML
    private Color x3;
    @FXML
    private BorderPane BorderPaneTab;
    @FXML
    private BorderPane BorderPaneConnexion;
    @FXML
    private TextField txtFieldCreateName;
    @FXML
    private Label lblCreationProcessFeedBack;
    @FXML
    private Label lblCreationIdFeedBack;
    @FXML
    private Label lblCommitProcessFeedBack;
    @FXML
    private Label lblCommitIdFeedBack;
    @FXML
    private TextField txtFieldCommitSourceFolder;
    @FXML
    private Label lblCommitSourceFolderFeedback;
    @FXML
    private Label lblPullProcessFeedBack;
    @FXML
    private Label lblPullIdFeedBack;
    @FXML
    private TextField txtfPullDestinationFolder;
    @FXML
    private Label lblPullDestinationFolderFeedBack;
    @FXML
    private ComboBox<String> comboBoxCommitProjectName;
    @FXML
    private ComboBox<String> comboBoxPlullProjectName;
    @FXML
    private ListView<?> lstProjectList;
    private boolean connectedOnce;
    private SBoxServerClient queryClient;

    //</editor-fold>
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BorderPaneConnexion.setVisible(true);
        BorderPaneTab.setVisible(false);

        obs = new StepObserver(progress, progressPourcentage, progressText);

        controllers = new HashMap<>();

        //<editor-fold defaultstate="collapsed" desc="[CONTROLLERS]création des controllers avec leur messenger">
        SBoxConnQueryController queryCtrl = new SBoxConnQueryController();        
        SBoxConnexionController ConnexionCtrl = new SBoxConnexionController(new ConnexionMessenger(lblFeedBackConnection));
        PullController pullCtrl = new PullController(new PullMessenger(lblPullIdFeedBack, lblPullProcessFeedBack, lblPullDestinationFolderFeedBack), obs);
        CreateController createCtrl = new CreateController(new CreateMessenger(lblCreationIdFeedBack, lblCreationProcessFeedBack),queryCtrl);
        CommitController commitCtrl = new CommitController(new CommitMessenger(lblCommitIdFeedBack, lblCommitSourceFolderFeedback, lblCommitProcessFeedBack), obs,queryCtrl);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Ajout des contrôleurs à la map puis envoi d'une liste au contrôleur de connexion">        
        putAllController(ConnexionCtrl, createCtrl, commitCtrl, pullCtrl, queryCtrl);

        ConnexionCtrl.setControllers(new ArrayList(controllers.values()));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="[CONTROLLERS] liaison des champs d'entrées aux contrôleurs">
        ConnexionCtrl.setInputs(txtFPort, txtFIpServeur, txtFPseudo);
        pullCtrl.setInputs(txtFieldPullId, txtfPullDestinationFolder);
        commitCtrl.setInputs(txtFieldCommitId, txtFieldCommitSourceFolder);
        createCtrl.setInputs(txtFieldCreateName);
        queryCtrl.setInputs(txtFPort, txtFIpServeur,txtFPseudo);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="[CONTROLLERS] liaison des panneaux pour permettre le switch à la connexion">
        ConnexionCtrl.setPanes(BorderPaneConnexion, BorderPaneTab);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="[CONTROLLERS] ajout des sous-contrôlers en tant qu'écouteur de ce contrôler">
        for (SubController ctrl : controllers.values()) {
            this.addObserver(ctrl);
        }
        //</editor-fold>

        java.util.List<String> listeDeStrings = new ArrayList<String>(Arrays.asList(new String[]{"", "", "", ""}));
        ((ListView) lstProjectList).setItems(FXCollections.observableArrayList(listeDeStrings));

    }

    //<editor-fold defaultstate="collapsed" desc="FXML Methods">
    @FXML
    private void seConnecterAuServeur(ActionEvent event) {
        connect();
        if (client != null) {
            String className = SBoxConnexionController.class.getSimpleName();
            SBoxServerClient connected = controllers.get(className).getClient();
            connected.sendToServer("QUIT");
        }
    }

    @FXML
    private void createProject(ActionEvent event) {
        connect();
        setAction("CREATE");
    }

    @FXML
    private void commit(ActionEvent event) {
        connect();
        setAction("COMMIT");
    }

    @FXML
    private void pull(ActionEvent event) {
        connect();
        setAction("PULL");
    }

    @FXML
    private void choseADirectoryToPullProject(ActionEvent event) {
        setLocation(txtfPullDestinationFolder);
    }

    @FXML
    private void choseAProjectLocationToCommit(ActionEvent event) {
        setLocation(txtFieldCommitSourceFolder);
    }

    @FXML
    private void quitter(ActionEvent event) {
        if (client != null) {
            client.sendToServer("QUIT");
        }
        setAction("QUIT");  // force query connexion to quit
        System.exit(0);
    }

    @FXML
    private void setTxtFieldCommit(ActionEvent event) {
        if (comboBoxCommitProjectName.getValue() != null) {
            String project = ProjectParser.formatSelectedProject(comboBoxCommitProjectName.getValue());
            txtFieldCommitId.setText(project);
        }
    }

    @FXML
    private void setTxtFieldPull(ActionEvent event) {
        if (comboBoxPlullProjectName.getValue() != null) {
            String project = ProjectParser.formatSelectedProject(comboBoxPlullProjectName.getValue());
            txtFieldPullId.setText(project);
        }
    }

    @FXML
    private void setCreateTab(Event event) {
        if (this.connectedOnce) {
            querying(lstProjectList);
        }
    }

    @FXML
    private void setCommitTab(Event event) {
        querying(comboBoxCommitProjectName);
    }

    @FXML
    private void setPullTab(Event event) {
        querying(comboBoxPlullProjectName);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NOT FXML Methods">
    private void querying(Control listingProject) {
        setCurrentProjectList(listingProject);
        setAction("QUERY");
    }

    private void putAllController(SubController... controller) {
        for (SubController subController : controller) {
            controllers.put(subController.getClass().getSimpleName(), subController);
        }
    }

    public void exitApplication(ActionEvent event) {
        quitter(event);
    }

    private <T extends Control> void setLocation(T urlStoreLabel) {
        DirectoryChooser dialog = new DirectoryChooser();
        File file;
        if (null != (file = dialog.showDialog(BorderPaneTab.getScene().getWindow()))) {
            if (urlStoreLabel instanceof Label) {
                ((Label) urlStoreLabel).setText(file.getPath());
            } else if (urlStoreLabel instanceof TextInputControl) {
                ((TextInputControl) urlStoreLabel).setText(file.getPath());
            }
        }
    }

    public void quitter() {
        quitter(new ActionEvent());
    }

    private void setAction(String action) {
        setChanged();
        notifyObservers(action);
    }

    private void setCurrentProjectList(Control listeur) {
        String className = SBoxConnQueryController.class.getSimpleName();
        ((SBoxConnQueryController) controllers.get(className)).setCibledComboboxForProjects(listeur);

    }
    
    private void connect() {
        setAction("CONNECT");
        
        // the client is conneceted through the connexion process
        String className = SBoxConnexionController.class.getSimpleName();   // "ConnexionController"
        client = controllers.get(className).getClient();
        
        if(client != null && !connectedOnce){
            // query ==============================================================================
            setCurrentProjectList(lstProjectList);
            String queryClassName = SBoxConnQueryController.class.getSimpleName();
            Observer queryer = (SBoxConnQueryController) controllers.get(queryClassName);
            
            ((NextOkActionnableTask)client.getTask()).setNextOkCloseable(true);
            
            client.addObserverOnTask(new NextOkQueryable(client, queryer));
            
            // disconnexion =======================================================================
            client.addObserverOnTask(new NextOkOrBadDisconnector(client));
        }
        if (client != null) {
            connectedOnce = true;
        }

    }
//</editor-fold>
}
