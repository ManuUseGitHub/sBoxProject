/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.commit;

import Server.Constantes;
import Server.notimpl.base.Server;
import Server.notimpl.base.ServerTCPGroupController;
import util.Parser;
import Server.ZipAnalysis;
import Versionning.ProjectManager;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverutilities.ThreadingCountDown;

/**
 *
 * @author MAZE2
 */
public class CommitTaskController extends ServerTCPGroupController {

    private final Parser parser;
    private ZipFluxOut zipFluxOut;
    private final Map<String, ProjectManager> projectManagers;
    private ThreadingCountDown countdown;
    private boolean firstLineRead;

    public CommitTaskController(Server server) {
        super(server, new CommitTaskAnalyser());
        parser = new Parser();
        countdown = new ThreadingCountDown(10, 1000);
        projectManagers = new HashMap<>();
    }

    @Override
    public void update(Observable obs, Object o) {
        super.update(obs, o);

        String who = getWho();
        String message = getMessage();
        String analysed = getAnalyseResult();

        boolean isListeningFlux = projectManagers.containsKey(who);

        if (!isListeningFlux) {
            handle_simple(who, message, analysed);
        } else {
            handleFluxListening(who, message, analysed);
        }
    }

    private void handle_simple(String who, String message, String analysed) {
        switch (analysed) {
            case "COMMIT":
                firstLineRead = true;
                onCommitAnalysed(message, who);
                break;
            case "COMMIT_ID_ERROR":
                respond("ERR C1 identifiant invalide", who);
                break;
            case "COMMIT_VERSION_ERROR":
                respond("ERR C1 la version est invalide", who);
                break;
        }
    }

    private void handleFluxListening(String who, String message, String analysed) {
        switch (analysed) {
            case "ZIP":
                if (message.equals("###")) {
                    onEndOf64BasedFile(who);

                    // UPGRADE de la version
                    synchronized (projectManagers) {
                        projectManagers.get(who).validate();
                        projectManagers.remove(who);
                    }
                    countdown.end(null);

                } else {
                    onLineOf64Based(message);
                }
                break;
            default:
                synchronized (projectManagers) {
                    projectManagers.remove(who);
                }
                break;
        }
    }

    private void onCommitAnalysed(String message, String who) {
        String[] parsed = parser.parseMultipleGroups(message, CommitTaskAnalyser.COMMIT);

        String projectName = parsed[0];
        int version = Integer.parseInt(parsed[1]);

        String fileName = String.format("%s/%s/%s %d.zip", Constantes.REPOSITORIES_LOCATION, who, projectName, version + 1);

        synchronized (projectManagers) {
            projectManagers.put(who, new ProjectManager(who, String.format("%s/%s", Constantes.REPOSITORIES_LOCATION, who)));
            projectManagers.get(who).setProjectInfo(projectName, version);
            if (projectManagers.get(who).isNextVersionexist()) {
                projectManagers.remove(who);

                respond("ERR C1 Vous ne devez soumettre que les dernieres versions", who);
            } else {
                projectManagers.get(who).putIncommingUpperVersion();
                zipFluxOut = new ZipFluxOut();
                zipFluxOut.setup(new File(fileName));
                respond("OK C3 committing", who);
            }
        }
    }

    private void onEndOf64BasedFile(String who) {
        respond("OK C3 finalisation", who);
        zipFluxOut.close();
        respond("OK C3 fin d envoi", who);
        respond("OK", who);
    }

    private void onLineOf64Based(String message) {
        try {
            zipFluxOut.recevoirFichierCoteServeur(message);
            if(firstLineRead){
                countdown.setEndAction(disconnectWhenTimeIsUp(),getClass());
                firstLineRead = false;
            }
            countdown.reset();
            
        } catch (IOException ex) {
            Logger.getLogger(CommitTaskController.class.getName()).log(Level.SEVERE, null, ex);
            zipFluxOut.close();
            projectManagers.remove(getWho());
        }
    }

    private Runnable disconnectWhenTimeIsUp() {
        return () -> {
            zipFluxOut.close();
            System.err.println("<!> connexion aborded durring commit process");
            respond("ERR connexion aborded durring commit process", getWho());

            checkZipValidity();

        };
    }

    private void checkZipValidity() {
        if (projectManagers.get(getWho()).getwVersion() < 0) {
            String filename = String.format("%s/%s/%s", Constantes.REPOSITORIES_LOCATION, getWho(), zipFluxOut.getFile().getName());
            File file = new File(filename);

            if (file.exists() && !ZipAnalysis.isValid(file)) {
                System.err.println(String.format("<!>%s is invalid %s will be destroyed", filename, filename));
                respond("ERR Commited file is invalid the file will be destroyed", getWho());
                
                file.delete();
            }
        }
    }
}
