/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.notImplementation.InteractNetwork;

/**
 *
 * @author MAZE2
 */
public class ThreadingLightClient extends Observable implements InteractNetwork {

    private Socket clientSocket;
    private PrintWriter outFromClient;
    private String message;

    public ThreadingLightClient(int port, String ip) {
        try {
            clientSocket = new Socket(ip, port);

            new Thread(respondingAs()).start();
            new Thread(listening()).start();

        } catch (IOException ex) {
            Logger.getLogger(ThreadingLightClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ThreadingLightClient(int port) throws UnknownHostException {
        this(port,Inet4Address.getLocalHost().getHostAddress());
    }

    @SuppressWarnings("empty-statement")
    private Runnable respondingAs() {
        return () -> {
            try (PrintWriter output = outFromClient = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), Charset.forName("UTF-8")), true)) {
                outFromClient.println();
                while (!clientSocket.isClosed());
            } catch (IOException ex) {
                Logger.getLogger(ThreadingLightClient.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            } finally {
                outFromClient.close();
                outFromClient = null;
            }
        };
    }

    private Runnable listening() {
        return () -> {
            try (BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Charset.forName("UTF-8")))) {
                while (!clientSocket.isClosed()) {
                    message = inFromServer.readLine();
                    if (message != null) {
                        if (!message.isEmpty()) {
                            setChanged();
                            notifyObservers();
                        }
                    } else {
                        clientSocket.close();
                    }
                }
            } catch (SocketException ex) {
                message = ex.getMessage();
                setChanged();
                notifyObservers();
            } catch (IOException ex) {
                System.exit(0);
            }
        };
    }

    @Override
    public void sendResponse(String... args) {
        if (outFromClient != null) {
            outFromClient.println(args[0] + "\r\n");

            // on notifie les observeurs qu'on désir quitter
            if (args[0].matches("^(?i)(quit)$")) {
                message = "QUIT";
                setChanged();
                notifyObservers();
            }
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
