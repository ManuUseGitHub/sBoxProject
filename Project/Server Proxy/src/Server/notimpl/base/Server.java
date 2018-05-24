package Server.notimpl.base;

import Server.notimpl.InteractNetwork;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public abstract class Server extends Observable implements InteractNetwork {

    private String message;

    @Override
    public abstract void sendResponse(String... args);

    public abstract void beforeClosingClient(Socket client);

    protected void ecouterInFromClient(final BufferedReader inFromClient, final Socket client) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!client.isClosed()) {
                    // on lit. Si rien n'est submit, on block jusqu'à recevoir quelque chose
                    message = readSentence(inFromClient);
                    atListeningSomething(client);
                    handleSentenceFromClient(client);
                }
            }

            
        }).start();
    }
    
    protected abstract void atListeningSomething(Socket client);

    protected String readSentence(BufferedReader inFromClient) {
        try {
            return inFromClient.readLine();
        } catch (IOException ex) {
            return null;
        }
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
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sendToClient(Socket socket, String data) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8"));
            PrintWriter opw = new PrintWriter(osw, true);
            opw.println(data);
            opw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleSentenceFromClient(Socket client) {
        String who = getWhoFromSocket(client);

        if (message == null || message.matches("^(?i)(quit)$")) {
            handleQuitCase(client, who);
        } else if (message != null && !message.isEmpty()) {
            notifyLikeThis(client, message);
        }

    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    private void handleQuitCase(Socket client, String who) {
        String reason = message == null ? "'ABRUPT QUIT (NULL)' " : "QUIT";

        System.err.println(String.format("<< %s from %s\t|* %s disconnected", reason, who, who).trim());

        if (!client.isClosed()) {
            sendToClient(client, "OK QUIT");
        }
        beforeClosingClient(client);
        close(client);
    }
}
