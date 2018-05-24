/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.notimpl.base.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public abstract class ThreadServerGroupTCP extends Server implements Runnable {
    
    protected ServerSocket welcomeSocket;
    protected final Map<Socket, String> clients;

    public ThreadServerGroupTCP(int port) {
        clients = Collections.synchronizedMap(new HashMap<Socket, String>());
        try {

            welcomeSocket = new ServerSocket(port, 0, Inet4Address.getLocalHost());
            new Thread(this).start();
        } catch (IOException ex) {
            Logger.getLogger(SBoxProxyServer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    @Override
    public void sendResponse(String... args) {
        String who = args[1];
        for (Socket client : clients.keySet()) {
            if (clients.get(client).equals(who)) {
                atResponse(who);
                sendToClient(client, args[0]);
                break;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket newClient = welcomeSocket.accept();
                // création d'un flux de lecture sur le socket.
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(newClient.getInputStream(), Charset.forName("UTF-8")));
                String IntroductionResult = introduceNewNamedClient(inFromClient, newClient);
                if (IntroductionResult.matches("^OK .*$")) {
                    ecouterInFromClient(inFromClient, newClient);
                } else {
                    System.err.println(IntroductionResult.split(" ")[1] + " allready connected");
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    protected String getWhoFromSocket(Socket client) {
        return clients.get(client);
    }

    @Override
    protected void notifyLikeThis(Socket client, String clientSentence) {
        setChanged();
        String who = getWhoFromSocket(client);
        notifyObservers(new String[]{clientSentence, who});
    }

    protected String introduceNewNamedClient(BufferedReader inFromClient, Socket newClient) throws IOException {
        String who = inFromClient.readLine();
        synchronized (clients) {
            if (clients.values().contains(who)) {
                return "ERR " + who;
            }
            clients.put(newClient, who);
        }
        onAcceptingNewone(newClient,who);
        return "OK " + who;
    }

    @Override
    public void beforeClosingClient(Socket client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }

    protected abstract void onAcceptingNewone(Socket newClient,String who);

    protected abstract void atResponse(String who);
    
    
    @Override
    protected void atListeningSomething(Socket client) {
        atListeningSomething(getWhoFromSocket(client));
    }
    
    protected abstract void atListeningSomething(String who);
}
