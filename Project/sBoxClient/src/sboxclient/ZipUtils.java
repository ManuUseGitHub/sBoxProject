/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;

/**
 *
 * @author tonioush
 */
public final class ZipUtils {

    public static List<String> getListOfBase64Strings(File file) {
        Encoder encodeur = Base64.getEncoder();

        List<String> fichierEncoder = new ArrayList();
        ByteBuffer tampon = ByteBuffer.allocate(57);
        try (FileChannel canal = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {
            tampon.clear();
            while (canal.read(tampon) > 0) {
                fichierEncoder.add(encodeur.encodeToString(tampon.array()));
                //ici on doit envoyer la ligne au serveur
                tampon.position(0);
                tampon = ByteBuffer.allocate(57);

            }

        } catch (IOException e) {

        }
        return fichierEncoder;
    }

    

    public static ByteBuffer decodeListOfStrings(List<String> strings) {
        ByteBuffer decoded = ByteBuffer.allocate(strings.size() * 57);
        int position = 0;
        decoded.position(position);
        for (String s : strings) {
            decoded.put(decode(s));
            position += 57;
        }
        return decoded;
    }

    public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

        File file = new File(fileName);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();

    }

    public static void decodeInFile(String fileName, List<String> stringsToDecode) throws IOException {
        writeByteArraysToFile(fileName, decodeListOfStrings(stringsToDecode).array());
    }

    //méthode pour recoir un fichier
    public static void recevoirFichierCoteServeur() throws IOException {
        File file = new File("../Repositories/projetNom/data.zip");
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        boolean continuer = true;
        while (continuer) {//tant qu'on est pas arrivé à EOF (###)

            byte[] content = decode("String Base64 reçu");
            writer.write(content);
            continuer = false;
            writer.flush();

        }

        writer.close();
    }

    private static byte[] decode(String encoded) {
        Decoder decodeur = Base64.getDecoder();
        byte[] decoded = decodeur.decode(encoded);

        return decoded;
    }

//    public static String encodeFileToBase64Binary(File file) throws Exception {
//        String encodedfile = null;
//        try {
//            Encoder encodeur = Base64.getEncoder();
//            FileInputStream fileInputStreamReader = new FileInputStream(file);
//            byte[] bytes = new byte[(int) file.length()];
//            fileInputStreamReader.read(bytes);
//            encodedfile = encodeur.encodeToString(bytes);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            throw e;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            throw e;
//        }
//
//        return encodedfile;
//    }
}
