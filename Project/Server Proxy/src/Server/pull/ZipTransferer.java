package Server.pull;

import Server.notimpl.InteractNetwork;
import Stepping.implementations.VerboseStepper;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 *
 * @author MAZE2
 */
public class ZipTransferer {

    public static void sendFileTo(File file, String who, InteractNetwork server) {
        ZipTransferer.sendFileTo(file, who, server, null);
    }

    public static void sendFileTo(File file, String who, InteractNetwork interactor, VerboseStepper stepper) {

        Base64.Encoder encodeur = Base64.getEncoder();
        ByteBuffer tampon = ByteBuffer.allocate(57);
        if (file.exists()) {
            try (FileChannel canal = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {
                tampon.clear();
                int step = 0;
                while (canal.read(tampon) > 0) {

                    String line = encodeur.encodeToString(tampon.array());
                    interactor.sendResponse(line, who);

                    tampon.position(0);
                    tampon = ByteBuffer.allocate(57);

                    steppingProcess(stepper, step, file);

                }
                steppingEnd(stepper, step, file);

                interactor.sendResponse("###", who);
            } catch (IOException e) {
                interactor.sendResponse("ERR C1 Aucun fichier ne correspond", who);
            }
        }
    }

    private static void steppingProcess(VerboseStepper stepper, int step, File file) {
        if (stepper != null && stepper.getAdvancement() < 1.0) {
            stepper.steppingWhileProcessing(++step, String.format("transfer du fichier '%s'", file.getName()));
        }
    }

    private static void steppingEnd(VerboseStepper stepper, int step, File file) {
        if (stepper != null && stepper.getAdvancement() < 1.0) {
            stepper.steppingDirectly(++step, String.format("transfère du fichier '%s' terminé", file.getName()));
        }
    }
}
