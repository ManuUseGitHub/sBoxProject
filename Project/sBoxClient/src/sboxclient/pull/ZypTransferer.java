/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.pull;

import Stepping.implementations.SoftStepper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import sboxclient.server.SBoxServerClient;

/**
 *
 * @author MAZE2
 */
public class ZypTransferer {
    public static void zipFolder(final File folder, final File zipFile, SoftStepper steps) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            processFolder(folder, zipOutputStream, folder.getPath().length() + 1, steps);
        }
    }
    
    public static void sendFileToServer(File file, SBoxServerClient client, SoftStepper steps) {

        Base64.Encoder encodeur = Base64.getEncoder();
        ByteBuffer tampon = ByteBuffer.allocate(57);
        try (FileChannel canal = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {
            tampon.clear();
            int step = 0;
            while (canal.read(tampon) > 0) {

                String line = encodeur.encodeToString(tampon.array());

                steps.setAdvancementMessage(line);
                client.sendToServer(line);

                tampon.position(0);
                tampon = ByteBuffer.allocate(57);
                steps.updateAdvancement(++step);
            }
            client.sendToServer("###");
            steps.terminer();

        } catch (IOException e) {
        }
    }
    private static void processFolder(final File folder, final ZipOutputStream zipOutputStream, final int prefixLength, SoftStepper steps) throws IOException {
        for (final File file : folder.listFiles()) {
            if (file.isFile()) {
                steps.setQuickAdvancementMessage("mise en tempon : "+file.getName());
                
                final ZipEntry zipEntry = new ZipEntry(file.getPath().substring(prefixLength));
                zipOutputStream.putNextEntry(zipEntry);
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024 * 4];
                    int read = 0;
                    while ((read = inputStream.read(buffer)) != -1) {
                        zipOutputStream.write(buffer, 0, read);
                    }
                }
                zipOutputStream.closeEntry();
            } else if (file.isDirectory()) {
                processFolder(file, zipOutputStream, prefixLength, steps);
            }
        }
    }
}
