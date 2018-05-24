/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sboxclient.commit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAZE2
 */
public class ZipFluxOut {
    private FileOutputStream writer;
    private File endFile;

    //méthode pour recoir un fichier
    public void recevoirFichierCoteServeur(String s64based) throws IOException {
        Base64.Decoder decodeur = Base64.getDecoder();
        byte[] content = decodeur.decode(s64based);
        writer.write(content);
        writer.flush();
    }

    public void setup(File file) {
        try {
            writer = new FileOutputStream(file, true);
            endFile = file;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipFluxOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            writer.close();
            writer = null;
        } catch (IOException ex) {
            Logger.getLogger(ZipFluxOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the endFile
     */
    public File getEndFile() {
        return endFile;
    }

}
