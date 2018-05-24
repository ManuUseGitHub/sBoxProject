package node;

import Stepping.implementations.ConsoleClearer;
import java.io.File;
import node.ThreadServerOneTCP;
import node.environnement.SingletonWhohasList;
import node.received.WHOHAS.WhohasController;
import node.receiver.MultiCastSender;
import node.receiver.ThreadMCReceiver;
import node.receiving.INFO.InfoTaskController;
import node.receiving.PROVIDE.ProvideTaskController;
import node.receiving.RECOVER.RecoverTaskController;
import node.sending.RETRIEVE.for_transfer.RetrieveTaskController;
import node.receiving.SEEK.SeekTaskController;
import node.sending.ALIVE.ObserverAliveController;
import node.sending.SEEK.ThreadSeekTaskController;

/*ww  w. j  av  a  2 s .  com*/
public class Main {

    public static void main(String[] args) throws Exception {

        // valeurs par défaut
        int localNumber = 40;
        int localMainServerMachineNumber = 0;
        int numNode = 2;

        if (args != null && args.length>0) {
            localNumber = Integer.parseInt(args[0]);
            localMainServerMachineNumber = Integer.parseInt(args[1]);
            numNode = Integer.parseInt(args[2]);
        }
        
        int mcPort = numNode + 6000;

        
        System.setOut(ConsoleClearer.getINSTANCE().getRemember());
        String mcIP1Str = String.format("225.1.%d.%d", localNumber, localMainServerMachineNumber);
        String mcIP2Str = String.format("225.2.%d.%d", localNumber, localMainServerMachineNumber);

        System.out.println(String.format("UDP réception \ton\t%s\nUDP émission \ton\t%s\nnode port    \t\t%d",
                mcIP1Str, mcIP2Str, mcPort));
        
        ThreadServerOneTCP server2 = new ThreadServerOneTCP(mcPort);
        server2.addObserver(new RetrieveTaskController(server2));

        ThreadMCReceiver node = new ThreadMCReceiver(5000, mcIP2Str);
        MultiCastSender sender = new MultiCastSender(5000, mcIP1Str);
        MultiCastSender sender2 = new MultiCastSender(5000, mcIP2Str);

        node.addObserver(new ObserverAliveController(sender, numNode, mcIP1Str));       // 2 => Alive => 1
        node.addObserver(new InfoTaskController(sender2));                              // 2 => INFO => 2
        node.addObserver(new ProvideTaskController(sender2, mcPort));                   // 2 => PROVIDE => 2
        node.addObserver(new WhohasController(sender2, mcPort));                        // 2 => WHOHAS => 2
        node.addObserver(new SeekTaskController(sender2, mcPort));                      // 2 => SEEK => 2
        node.addObserver(new RecoverTaskController(sender2));                           // 2 => RECOVER => 2
        
        SingletonWhohasList.getINSTANTCE().setSender(sender2);
        
        new ThreadSeekTaskController(sender2, mcPort);
        new File(Constantes.REPOSITORY_LOCATION).mkdir();   // on force la création du dossier repository si'il n'existe pas
    }
}
