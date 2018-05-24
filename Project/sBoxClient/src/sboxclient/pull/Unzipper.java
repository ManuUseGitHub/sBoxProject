/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.pull;

//Importation des packages dont on va se servir
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

    public static void unzip(String zipFileName){
        String folderName = zipFileName.substring(0, zipFileName.indexOf('.'));
        File zip = new File(zipFileName);
        File folder = new File(folderName);
        folder.mkdir();
        try {
            unzip(zipFileName, folder.getPath());
            zip.delete();
        } catch (IOException ex) {
            folder.delete();
            Logger.getLogger(Unzipper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void unzip(String zipFileName, String folderName) throws FileNotFoundException, IOException {
        //http://patatos.over-blog.com/article-decompresser-un-fichier-zip-en-java-44249513.html
        File zipFile = new File(zipFileName);
        File folder = new File(folderName);
        
        // extractions des entrées du fichiers zip (i.e. le contenu du zip)
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile.getCanonicalFile()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {

                // On crée un fichier dans le répertoire de sortie "folder"
                File f = new File(folder.getCanonicalPath(), entry.getName());

                if (entry.isDirectory()) {
                    f.mkdirs();
                } else {
                    f.getParentFile().mkdirs();
                    writeFileRead(f,zis);
                }
            }
        }
    }

    private static void writeFileRead(File f,ZipInputStream input) throws IOException {
        //http://patatos.over-blog.com/article-decompresser-un-fichier-zip-en-java-44249513.html
        // On écrit le contenu du nouveau fichier
        try {
            try (OutputStream fos = new FileOutputStream(f)) {
                final byte[] buf = new byte[8192];
                int bytesRead;
                while (-1 != (bytesRead = input.read(buf))) {
                    fos.write(buf, 0, bytesRead);
                }
            }
        } catch (final IOException ioe) {
            f.delete();
            throw ioe;
        }
    }
}