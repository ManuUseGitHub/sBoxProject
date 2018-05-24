/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.pull.PullTaskController;
import Server.commit.CommitTaskController;
import Server.create.ObserverCreateTaskController;
import Server.notimpl.base.Controller;
import Server.pull.PulledTaskController;
import node.ALIVE.AliveController;
import node.INFO.ThreadInfoTaskController;
import node.MultiCastSender;
import node.TCP.RETRIEVE.RetrieveTaskController;
import node.WHOHAS.WhohasController;
import Server.query.QueryController;
import Stepping.Stepper;
import Stepping.implementations.StepperImpl;
import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.receiving.PROVIDE.for_transfer.ProvideTaskController;
import node.receiving.RESTORE.RestoreTaskController;
import node.sending.RECOVER.ThreadRecoverTaskController;

/**
 *
 * @author Maze
 */
public class Programm {

    private static String mcIP2Str;
    private static String mcIP1Str;
    private static int localNumber;
    private static int localMainServerMachineNumber;

    //http://windows.microsoft.com/fr-fr/windows/open-port-windows-firewall#1TC=windows-7
    public static void main(String[] args) {
        int port = 4000;
        int transferToNodePort = 5000;
        intitialize();

        System.out.println(String.format("UDP recepts on \t\t%s\nUDP emit on \t\t%s\nmulticast node port\t%d",
                mcIP1Str, mcIP2Str, 5000));

        anounceServers(port);

        // node 0
        ThreadServerOneTCP server2 = new ThreadServerOneTCP(transferToNodePort);
        SBoxProxyServer server = new SBoxProxyServer(port);

        ThreadMCReceiver receiver = new ThreadMCReceiver(5000, mcIP1Str);
        ThreadMCReceiver receiver2 = new ThreadMCReceiver(5000, mcIP2Str);
        MultiCastSender sender = new MultiCastSender(5000, mcIP2Str);

        server2.addObserver(new RetrieveTaskController(sender));

        server.addObserver(new ObserverCreateTaskController(server));
        server.addObserver(new CommitTaskController(server));
        server.addObserver(new PullTaskController(server, sender));
        server.addObserver(new QueryController(server));
        
        // dualReceiving
        Controller pulledCtrl = new PulledTaskController(sender,server);
        server.addObserver(pulledCtrl);                         
        receiver2.addObserver(pulledCtrl);
        
        Stepper stepper = buildStepperUsingServer(server);
        
        receiver.addObserver(new ThreadInfoTaskController(sender));                             // 1 => INFO => 2
        receiver.addObserver(new AliveController(sender));                                      // 1 => ALIVE => 2
        receiver2.addObserver(new WhohasController(sender, transferToNodePort));                // 2 => WHOHAS => 2
        receiver2.addObserver(new ProvideTaskController(sender, transferToNodePort, stepper));  // 2 => PROVIDE => 2
        receiver2.addObserver(new RestoreTaskController());
        
        new File(Constantes.REPOSITORIES_LOCATION).mkdir();   // on force la création du dossier repositories si'il n'existe pas
        new File(Constantes.REPOSITORIES_PULL_LOCATION).mkdir();
        
        new ThreadRecoverTaskController(sender);
    }

    private static void anounceServers(int port) {
        try {
            System.out.println(String.format("TCP: %s port : %d", Inet4Address.getLocalHost().getHostAddress(), port));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Programm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void intitialize() {
        Scanner scan = new Scanner(System.in);
        System.out.println("=== CONFIGURATION ===");
        System.out.println("press (Y) to process (N) or just <return> to skip configuration");

        //default
        localNumber = 40;
        localMainServerMachineNumber = 0;

        if (scan.nextLine().equals("Y")) {
            System.out.print("Enter the local number:");
            localNumber = scan.nextInt();

            System.out.print("Enter the local machine number:");
            localMainServerMachineNumber = scan.nextInt();
        }

        mcIP1Str = String.format("225.1.%d.%d", localNumber, localMainServerMachineNumber);
        mcIP2Str = String.format("225.2.%d.%d", localNumber, localMainServerMachineNumber);
    }

    private static Stepper buildStepperUsingServer(SBoxProxyServer server) {
        StepperImpl stepperForView = new StepperImpl();
        stepperForView.addObserver((Observable o, Object arg) -> {
            String [] subMessage = stepperForView.getAdvancementMEssage().split("=>");
            
            server.sendResponse("OK C4 "+subMessage[0].replace("."," "),subMessage[1]); // message , who
        });
        return stepperForView;
    }
}
