package node.receiving.RETRIEVE;



import Stepping.implementations.VerboseStepper;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import node.notImplementation.InteractNetwork;

/**
 *
 * @author MAZE2
 */
public class ZipTransferer {

    public static void sendFileTo(File file, String who, InteractNetwork server) {
        ZipTransferer.sendFileTo(file, who, server, null);
    }

    public static void sendFileTo(File file, String who, InteractNetwork TCPActor, VerboseStepper stepper) {

        Base64.Encoder encodeur = Base64.getEncoder();
        ByteBuffer tampon = ByteBuffer.allocate(57);
        if (file.exists()) {
            try (FileChannel canal = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {
                tampon.clear();
                //<editor-fold defaultstate="collapsed" desc="setpping start">
                int step = 0;
                stepper.steppingDirectly(step, String.format("transfère du fichier '%s' commencé", file.getName()));// step
//</editor-fold>
                while (canal.read(tampon) > 0) {

                    String line = encodeur.encodeToString(tampon.array());
                    TCPActor.sendResponse(line, who);

                    tampon.position(0);
                    tampon = ByteBuffer.allocate(57);

                    //<editor-fold defaultstate="collapsed" desc="stepping process">
                    if (stepper.getAdvancement() < 1.0) {
                        stepper.steppingWhileProcessing(++step, String.format("transfer du fichier '%s'", file.getName()));
                    }
//</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="stepping end">
                stepper.steppingDirectly(++step, String.format("transfère du fichier '%s' terminé", file.getName()));
//</editor-fold>
                TCPActor.sendResponse("###", who);
            } catch (IOException e) {
                TCPActor.sendResponse("ERR C1 Aucun fichier ne correspond", who);
            }
        }
    }
}
