/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.notimpl.Network;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public class ThreadMCReceiver extends Observable implements Runnable, Network {

    private MulticastSocket mcSocket;
    private InetAddress adress;
    private String message;
    private String ipFrom;

    public ThreadMCReceiver(int mcPort, String mcIPStr) {
        try {
            joining(mcPort, mcIPStr);
            new Thread(this).start();
        } catch (IOException ex) {
            Logger.getLogger(ThreadMCReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

                mcSocket.receive(packet); // on attend de recevoir quelque chose
                message = new String(packet.getData(), packet.getOffset(), packet.getLength()).trim();
                ipFrom = (packet.getAddress()).getHostAddress();

                if (message == null || message.matches("QUIT")) {
                    leave(mcSocket, adress);
                    break;
                }

                setChanged();
                notifyObservers();

            } catch (IOException ex) {
                leaveByForce();
                break;
            }
        }
    }

    private void joining(int mcPort, String mcIPStr) throws UnknownHostException, IOException {
        adress = Inet4Address.getByName(mcIPStr);
        mcSocket = new MulticastSocket( mcPort);
        
        mcSocket.joinGroup(adress);
        System.out.println("Multicast Receiver running at:" + mcSocket.getLocalSocketAddress());
    }

    private void leave(MulticastSocket mcSocket, InetAddress adress) throws IOException {
        mcSocket.leaveGroup(adress); // on quitte le groupe
        mcSocket.close();
    }

    private void leaveByForce() {
        try {
            leave(mcSocket, adress);
        } catch (IOException ex1) {
            Logger.getLogger(ThreadMCReceiver.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public String getIpFrom() {
        return ipFrom;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
