/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Versionning.XML.impl.profil;

import Versionning.XML.XMLExporter;
import Versionning.dataource.ProfilsList;
import java.io.Writer;

/**
 *
 * @author MAZE2
 */
public class ProfilsExporter extends XMLExporter {

    public ProfilsExporter() {
        super(new ProfilXMLParser());
    }
    public <T extends ProfilsList,Profil>void exporter(T model, Writer writer){
        super.exporter(model, writer);
    }
}
