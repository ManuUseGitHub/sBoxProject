/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

/**
 *
 * @author MAZE2
 */
public interface ServerClient {

    public void onHandleError(SocketException ex);

    public void onReadingSomething();

    public void onReading(BufferedReader inFromServer) throws IOException;
}
