package node.notImplementation.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.notImplementation.InteractNetwork;

/**
 *
 * @author MAZE2
 */
public abstract class ObservableServer extends Observable implements InteractNetwork {

    private String message;

    @Override
    public abstract void sendResponse(String... args);
    
    public abstract void onClosingClient(Socket client);

    protected void ecouterInFromClient(final BufferedReader inFromClient, final Socket client) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!client.isClosed()) {

                    // on lit. Si rien n'est submit, on block jusqu'à recevoir quelque chose
                    String clientSentence = readSentence(inFromClient);
                    if (clientSentence == null || clientSentence.matches("^(?i)(quit)$")) {
                        String data = getWhoFromSocket(client) + " disconnected";

                        System.err.println(data.trim());
                        close(client);
                        if (!client.isClosed()) {
                            sendToClient(client, "OK");
                        }
                        onClosingClient(client);
                    } else if (!clientSentence.isEmpty()) {
                        notifyLikeThis(client, clientSentence);
                    }
                }
            }
        }).start();
    }

    protected abstract String getWhoFromSocket(Socket client);

    /**
     * Define the way to notify observers. Because it <b color=red>can
     * differe</b>
     * regarding the type of messages you want to commit or if you let know the
     * server state by its attributes
     *
     * @param client
     * @param clientSentence
     */
    protected abstract void notifyLikeThis(Socket client, String clientSentence);

    protected void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ObservableServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String readSentence(BufferedReader inFromClient) {
        try {
            return inFromClient.readLine();
        } catch (IOException ex) {
            return null;
        }
    }

    protected void sendToClient(Socket socket, String data) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8"));
            PrintWriter opw = new PrintWriter(osw, true);
            opw.println(data);
            opw.flush();
        } catch (IOException ex) {
            Logger.getLogger(ObservableServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
