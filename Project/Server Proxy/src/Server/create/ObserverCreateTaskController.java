/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.create;

import Server.Constantes;
import Server.notimpl.base.Server;
import Server.notimpl.base.ServerTCPGroupController;
import util.Parser;
import Server.UserFileManager;
import Versionning.XML.impl.profil.ProfilLoader;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.io.File;
import java.util.Observable;

/**
 *
 * @author MAZE2
 */
public class ObserverCreateTaskController extends ServerTCPGroupController {

    private final Parser parser;

    public ObserverCreateTaskController(Server server) {
        super(server, new CreateTaskAnalyser());
        parser = new Parser();
    }

    @Override
    public void update(Observable obs, Object o) {
        super.update(obs, o);

        String who = getWho(), message = getMessage(), analysed = getAnalyseResult();

        switch (analysed) {
            case "CREATE":
                String projectName = parser.parseOneGroup(message,CreateTaskAnalyser.CREATE);
                new File(Constantes.REPOSITORIES_LOCATION + "/" + who).mkdirs();

                UserFileManager.createWhoseXML(who);
                if (!projectExists(who, projectName, 0)) {
                    Project project = new Project(0, projectName);
                    UserFileManager.createWhoseXMLWithProject(who, project);
                    respond("OK Le projet a bien ete cree",who);
                }
                break;
            case "CREATE_ID_ERROR":
                respond("ERR identifiant invalide",who);
                break;
        }
    }

    private boolean projectExists(String who, String projectName, int version) {

        Profil profil = ProfilLoader.load(new File(String.format("%s/%s/%s.xml", Constantes.REPOSITORIES_LOCATION, who,who)));
        Project project = profil.getProject(projectName, version);

        boolean exists = project != null;
        if (exists) {
            if (project.getVersion() == 0) {
                respond("ERR le projet est deja cree",who);
            } else {
                respond("ERR le projet existe deja",who);
            }
            return exists;
        }
        return exists;
    }
}
