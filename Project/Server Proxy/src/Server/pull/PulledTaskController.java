package Server.pull;

import Server.Analysis;
import Server.Constantes;
import Server.ForWhoseProjectModel;
import util.Parser;
import Server.notimpl.base.MCController;
import Server.notimpl.base.Server;
import Server.notimpl.base.ServerTCPGroupController;
import java.io.File;
import java.util.Observable;
import node.MultiCastSender;

/**
 *
 * @author MAZE2
 */
public class PulledTaskController extends MCController {

    private final Server server;
    private ForWhoseProjectModel project;
    private final Parser parser;

    public PulledTaskController(MultiCastSender sender, Server server) {
        super(new PullTaskAnalyser(),sender);
        this.server = server;
        this.parser = new Parser();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof EndPullProcess)) {
            super.update(o, arg);

            String analysed = getAnalyseResult();

            switch (analysed) {
                case "PULL":
                    String[] parsed = parser.parseMultipleGroups(getMessage(), PullTaskAnalyser.PULL);
                    project = new ForWhoseProjectModel(((String[])arg)[1], parsed[0], parsed[1]);
                    break;
                case "TIRE":
                    transferToPC();
                    break;
            }
        }
    }

    public void transferToPC() {
        String who = project.getWhose();
        String version = project.getProjectVersion();
        String projectName = project.getProjectName();
        
        server.sendResponse("OK C1 ", who);

        File file = new File(String.format("%s/%s/%s %s.zip", Constantes.REPOSITORIES_PULL_LOCATION, who,projectName,version));
        long taille = Analysis.size(file.toPath()) / 57 - 1;

        server.sendResponse("OK " + taille, who);
        server.sendResponse("OK C3 Pulling", who);

        ZipTransferer.sendFileTo(file, who, server);
        server.sendResponse("OK C3 Le pulling est fini", who);
        server.sendResponse("OK",who);
        file.delete();
    }
}
