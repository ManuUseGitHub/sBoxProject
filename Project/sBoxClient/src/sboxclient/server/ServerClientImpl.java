/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;

/**
 *
 * @author MAZE2
 */
public abstract class ServerClientImpl implements ServerClient{
    private final Socket clientSocket;
    private PrintWriter outFromClient;
    private String sentence;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public ServerClientImpl(int port, String ip, String who) throws IOException {
        clientSocket = new Socket(ip, port);

        new Thread(respondingAs(who)).start();
        new Thread(listening()).start();
    }

    public void sendToServer(String sentence) {
        if (outFromClient != null) {
            outFromClient.println(sentence + "\r\n");
        }
    }

    /**
     * @return the sentence
     */
    public String getSentence() {
        return sentence;
    }

    /**
     * @param sentence the sentence to set
     */
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
    
    private Runnable respondingAs(String identifier) {
        return () -> {
            try (PrintWriter output = outFromClient = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), Charset.forName("UTF-8")), true)) {
                onRespondingAs(identifier);
            } catch (IOException ex) {
                onBrutalError(ex);
            } finally {
                onEndingCommunication();
            }
        };
    }

    @SuppressWarnings({"empty-statement", "null"})
    public void onRespondingAs(String identifier) {
        
        // if an identifier is set, respond it first in order to identify
        if (identifier != null || !identifier.trim().equals("")) {
            outFromClient.println(identifier);
        }
        while (!clientSocket.isClosed());
    }
    
    public void onResponding() {
        onRespondingAs("");
    }
    
    @Override
    public void onReading(BufferedReader inFromServer) throws IOException {
        while (!clientSocket.isClosed()) {
            setSentence(inFromServer.readLine());
            if (getSentence() != null) {
                onReadingSomething();
            } else {
                onReadingSomethingFail();
            }
        }
    }
    
    private Runnable listening() {
        return () -> {
            try (BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), Charset.forName("UTF-8")))) {
                onReading(inFromServer);
            } catch (SocketException ex) {
                onHandleError(ex);
                
            } catch (IOException ex) {
                onBrutalError(ex);
            }
        };
    }

    private void onEndingCommunication() {
        outFromClient.close();
        outFromClient = null;
    }
    
    private void onBrutalError(IOException ex) {
        System.exit(0);
    }
   
    private void onReadingSomethingFail() throws IOException {
        clientSocket.close();
    }
}
