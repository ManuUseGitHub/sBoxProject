package Server;

import Server.notimpl.base.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServerOneTCP extends Server implements  Runnable {

    private ServerSocket welcomeSocket;
    private Socket client;
    private String sentence;

    public ThreadServerOneTCP(int port) {
        try {
            welcomeSocket = new ServerSocket(port);
            new Thread(this).start();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServerOneTCP.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {                
                if (client == null || client.isClosed()) {
                    System.out.println("Acception");
                    client = welcomeSocket.accept();
                    System.out.println("connected");

                    // création d'un flux de lecture sur le socket.
                    try {
                        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream(), Charset.forName("UTF-8")));
                        ecouterInFromClient(inFromClient, client);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
        }
    }

    @Override
    public void sendResponse(String... data) {
        sendToClient(client, data[0]);
    }

    @Override
    protected String getWhoFromSocket(Socket client) {
        return String.format("Client at %s on port %d", client.getInetAddress().getHostName(), welcomeSocket.getLocalPort());
    }

    @Override
    protected void notifyLikeThis(Socket client, String clientSentence) {
        this.sentence = clientSentence;
        setChanged();
        notifyObservers();
    }

    @Override
    public void beforeClosingClient(Socket client) {
    }

    public void closeClientConnexion() {
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServerOneTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getMessage() {
        return sentence;
    }

    @Override
    protected void atListeningSomething(Socket client) {
        // TODO setActif connexion
    }
}
