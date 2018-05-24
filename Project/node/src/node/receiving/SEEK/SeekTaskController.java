/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiving.SEEK;

import java.util.Observable;
import node.Constantes;
import node.notImplementation.base.MCController;
import node.receiver.MultiCastSender;
import node.receiver.ThreadMCReceiver;

/**
 *
 * @author MAZE2
 */
public class SeekTaskController extends MCController {

    private final SeekTaskParser parser;
    private final int localPort;
    private final InfoSenderFromProjects infoSender;
    
    public SeekTaskController(MultiCastSender sender,int port) {
        super(new SeekTaskAnalyser(), sender);
        parser = new SeekTaskParser();
        this.localPort = port;
        this.infoSender = new InfoSenderFromProjects(sender);
    }    

    /**
     * @return the localPort
     */
    public int getLocalPort() {
        return localPort;
    }
    
    @Override
    public void update(Observable o, Object args) {
        super.update(o, args);
        String msg = getMessage();
        
        if (getAnalyseResult().equals("SONDE")) {
            if (o instanceof ThreadMCReceiver) {
                String parsed = parser.parseSeek(msg);
                if(Integer.parseInt(parsed) != localPort){
                    System.out.println(String.format("Seek request from %s\n",parsed));
                    infoSender.seekAndSendProjectRequest(Constantes.REPOSITORY_LOCATION);
                }
            }
        }
    }
}
