package Versionning.XML;

import Versionning.ConverterToXML;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import java.io.Serializable;
import java.io.StringWriter;

import org.w3c.dom.Document;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLExporter {
    private final ConverterToXML converter;

    public XMLExporter(ConverterToXML converter) {
        this.converter = converter;
    }
    
    public <T extends Serializable>void exporter(T model,Writer writer) {
        if (model == null || writer == null) {
            throw new IllegalStateException();
        }
        try (Writer out = writer) {
            Document xmlDoc = converter.modelToXml(model);

            //Set OutputFormat
            OutputFormat outFormat = new OutputFormat(xmlDoc);
            outFormat.setIndenting(true);

            Iterator<String> contenu = getContentFromDoc(xmlDoc);

            while (contenu.hasNext()) {
                out.write(contenu.next() + "\r\n");// écrire contenu + retour chariot
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private Iterator<String> getContentFromDoc(Document doc){
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            String[] splits = sw.toString().split("\r\n");
            return Arrays.asList(splits).iterator();
        } catch (IllegalArgumentException | TransformerException ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }
}
