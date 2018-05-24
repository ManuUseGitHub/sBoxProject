package node.notImplementation.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author MAZE2
 */
public abstract class ConverterToXML {
    public <T extends Serializable >Document modelToXml(T model) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document xmlDoc = docBuilder.newDocument();
        
        defineModel(model,xmlDoc);
        
        return xmlDoc;
    }
    
    protected abstract <T extends Serializable>void defineModel(T model,Document xmlDoc);

    protected String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    protected void appendChildrenTo(Element parentNode, Node... children) {
        for (Node child : children) {
            parentNode.appendChild(child);
        }
    }
}
