package Versionning.XML;

import Versionning.LecteurFichier;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author MAZE2
 */
public class XMLFileReader extends LecteurFichier {

    public NodeList readXMLRootNode(String nodeListElementName, Reader reader) {
        try (Reader r = reader) {
            StringBuilder sb = new StringBuilder();
            String ligne;

            while ((ligne = readLine(r)) != null) {        // lecture charactère par charactère
                sb.append(ligne);
                sb.append("\n");
            }

            Document xmlDoc = getDocumentFromString(sb.toString());
            return xmlDoc.getElementsByTagName(nodeListElementName);
        } catch (IOException ex) {
            Logger.getLogger(XMLFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(XMLFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private Document getDocumentFromString(String s) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(s));

        Document doc = db.parse(is);
        return doc;
    }
}
