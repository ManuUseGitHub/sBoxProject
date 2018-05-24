/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.query;

import Server.Constantes;
import util.Parser;
import Server.notimpl.base.Server;
import Server.notimpl.base.ServerTCPGroupController;
import Versionning.XML.impl.profil.ProfilLoader;
import Versionning.dataource.Profil;
import Versionning.dataource.Project;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author MAZE2
 */
public class QueryController extends ServerTCPGroupController implements Observer {

    private final Parser taskParser;

    public QueryController(Server server) {
        super(server, new QueryTaskAnalyser());
        taskParser = new Parser();
    }

    @Override
    public void update(Observable o, Object args) {
        super.update(o, args);
        String analysed = getAnalyseResult();
        String who = getWho();
        String pseudo = who;

        if (analysed.equals("DEMANDE")) {
            analyse(who);   // on va tester si who est une requête personnalisée
            if(getAnalyseResult().equals("REQUETE_PERSONALISEE")){
                pseudo = taskParser.parseOneGroup(who,QueryTaskAnalyser.WHOSE_QUERY);
            }
            respond("LIST " + makeTask(pseudo), who);
        }
        
    }

    private String makeTask(String who) {
        StringBuilder sb = new StringBuilder();
        
        if (new File(String.format("%s/%s/%s.xml", Constantes.REPOSITORIES_LOCATION, who, who)).exists()) {
            Profil profil = loadPRofile(String.format("%s/%s/%s.xml", Constantes.REPOSITORIES_LOCATION, who, who));

            for (Project p : profil.getProjects()) {
                sb.append(p.getListFormat());
            }
        }
        return sb.toString();
    }

    private Profil loadPRofile(String filePath) {
        File file = new File(filePath);

        ProfilLoader loader = new ProfilLoader();

        loader.setFilePath(file.toPath());

        return loader.load();
    }

}
