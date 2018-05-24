/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.INFO;

import Versionning.UnexistingProjectManager;
import node.receiving.INFO.ProjectsCheck.WHOHAS.WHOHASMessageSender;
import java.util.Observable;
import node.Constantes;
import node.TaskParser;
import node.environnement.SingletonWhohasList;
import node.notImplementation.base.MCController;
import node.receiver.MultiCastSender;
import node.receiver.ThreadMCReceiver;
import node.receiving.INFO.ProjectsCheck.UserFileManager;

/**
 *
 * @author MAZE2
 */
public class InfoTaskController extends MCController{

    private final TaskParser parser;
    private final WHOHASMessageSender whoHasMessageSender;
    private final SingletonWhohasList providingInfoTask = SingletonWhohasList.getINSTANTCE();
    private final UnexistingProjectManager unexisting;

    public InfoTaskController(MultiCastSender sender) {
        super(new InfoTaskAnalyser(),sender);
        this.parser = new TaskParser();
        this.whoHasMessageSender = new WHOHASMessageSender(getSender());
        this.unexisting = new UnexistingProjectManager(Constantes.REPOSITORY_LOCATION);
        this.unexisting.addObserver(whoHasMessageSender);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ThreadMCReceiver) {
            super.update(o, arg);

            String analysed = getAnalyseResult();
            switch (analysed) {
                case "INFO":    // currentWho n'aura changer que pour les messages Infos à ce stade
                    if (!providingInfoTask.isHandling(getMessage().replace("INFO", "WHOHAS"))) {
                        String[] parsed = parser.parseMultipleGroups(getMessage(), InfoTaskAnalyser.INFO);
                        UserFileManager.createWhoseXML(parsed);
                        
                        whoHasMessageSender.setWorkingOn(parsed);
                        unexisting.manageNotExistentPoject(parsed[0], parsed[1], Integer.parseInt(parsed[2]));
                    }
                    break;
            }
        }
    }
}
