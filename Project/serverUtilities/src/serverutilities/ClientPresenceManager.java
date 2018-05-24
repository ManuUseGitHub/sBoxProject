package serverutilities;

import java.io.IOException;
import java.net.Socket;
import serviceDefinition.ServiceDef;

/**
 *
 * @author MAZE2
 */
public class ClientPresenceManager {
    private final Socket client;
    private final ThreadingCountDown timeUp; 
    private final Runnable closeAction;

    /**
     * défini l'action à effectuer après le time up suite à une innactivité trop longue du socket connecté
     * @param client le socket connecté 
     * @param onCloseAction l'action a effectué lors après le time up
     */
    public ClientPresenceManager(Socket client,Runnable onCloseAction) {
        this.closeAction = onCloseAction;
        this.client = client;
        timeUp = new ThreadingCountDown(60, 1000);  // un countdown qui se termine après une minute
        timeUp.end();                               // initialisé à stoppé
        timeUp.setEndAction(clientPoolCloser(),getClass()); // initialisation de l'action à faire en fin du temps imparti
    }
    
    /**
     * relance le compte à rebours
     */
    public void reset(){
        timeUp.reset();
    }
    
    /**
     * met fin au compte à rebours en enlevant l'action à effectuer
     */
    public void abbord(){
        timeUp.end(null);
    }
    
    @ServiceDef(desc = "la connexion d'un client présent trop longtemps termine à la fin d'un timeout de 1 minute",
            params = {})
    private Runnable clientPoolCloser() {
        return () -> {
            try {
                client.close();
            } catch (IOException ex) {
            }finally{
                closeAction.run();
            }
        };
    }
}
