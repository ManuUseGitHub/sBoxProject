/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author MAZE2
 */
public class MultiCastSender {

    private final int mcPort;
    private final String host;
    
    public MultiCastSender(int mcPort, String host){
        this.mcPort = mcPort;
        this.host = host;
    }

    public void send(String message) throws SocketException, UnknownHostException, IOException {
        
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress mcIPAddress = InetAddress.getByName(host);
            byte[] msg = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
            packet.setAddress(mcIPAddress);
            packet.setPort(mcPort);
            socket.send(packet);
        }
    }
}
