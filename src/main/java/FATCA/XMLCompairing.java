package FATCA;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XMLCompairing {

    public static void main(String[] args) throws Exception {
        // Read the first file
        File file1 = new File("/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_LineriasedUPD.xml");
        DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
        dbFactory1.setIgnoringElementContentWhitespace(true);
        DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
        Document doc1 = dBuilder1.parse(file1);

        // Read the second file
        File file2 = new File("/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_Lineriased.xml");
        DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
        dbFactory2.setIgnoringElementContentWhitespace(true);
        DocumentBuilder dBuilder2 = dbFactory2.newDocumentBuilder();
        Document doc2 = dBuilder2.parse(file2);

        // Sort the elements
        sortElements(doc1);
        sortElements(doc2);

        // Sort the attributes for each element
        sortAttributes(doc1);
        sortAttributes(doc2);

        // Output the document to string
        StringWriter writer1 = new StringWriter();
        StringWriter writer2 = new StringWriter();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(doc1), new StreamResult(writer1));
        transformer.transform(new DOMSource(doc2), new StreamResult(writer2));
        String normalizedXML1 = writer1.toString();
        String normalizedXML2 = writer2.toString();
        // Compare the two normalized XML strings
        if (normalizedXML1.equals(normalizedXML2)) {
            System.out.println("The two XML files are the same.");
        } else {
            System.out.println("The two XML files are different.");
        }
    }

    private static void sortElements(Document doc) {
        NodeList nodes = doc.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            sortChildren(element);
        }
    }

    private static void sortChildren(Element element) {
        List<Element> children = new ArrayList<>();
        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                children.add((Element) node);
            }
        }
        Collections.sort(children, (o1, o2) -> o1.getTagName().compareTo(o2.getTagName()));
        for (Element child : children) {
            sortChildren(child);
            element.appendChild(child);
        }
    }

    private static void sortAttributes(Document doc) {
        NodeList nodes = doc.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            sortAttribs(element);
        }
    }

    private static void sortAttribs(Element element) {
        List<Attr> attributes = new ArrayList<>();
        for (int i = 0; i < element.getAttributes().getLength(); i++) {
            attributes.add((Attr)element.getAttributes().item(i));
        }
        Collections.sort(attributes, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        for (Attr attr : attributes) {
            element.setAttribute(attr.getName(), attr.getValue());
        }
    }
}