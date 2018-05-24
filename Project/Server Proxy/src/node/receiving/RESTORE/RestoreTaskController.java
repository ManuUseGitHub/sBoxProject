/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.RESTORE;

import Server.Constantes;
import util.Parser;
import Server.ThreadMCReceiver;
import Server.UserFileManager;
import Server.notimpl.base.MCController;
import Versionning.UnexistingProjectManager;
import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public class RestoreTaskController extends MCController {

    private final Parser parser;
    private final UnexistingProjectManager unexisting;
    private final ProjectRestorer restorer;

    public RestoreTaskController() {
        super(new RestoreTaskAnalyser(), null);
        this.parser = new Parser();
        restorer = new ProjectRestorer();
        unexisting = new UnexistingProjectManager(Constantes.REPOSITORIES_LOCATION);
        unexisting.addObserver(restorer);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ThreadMCReceiver) {
            super.update(o, arg);

            String analysed = getAnalyseResult();
            switch (analysed) {
                case "RESTORATION":
                    String[] parsed = parser.parseMultipleGroups(getMessage(), RestoreTaskAnalyser.RESTORE);
                    UserFileManager.createWhoseXML(parsed[0]);
                    
                    restorer.setWorkingOn(parsed);
                    unexisting.manageNotExistentPoject(parsed[0], parsed[1], Integer.parseInt(parsed[2]));

                    break;
            }
        }
    }
}
