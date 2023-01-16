package FATCA;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XMLComparison {
    public static void main(String[] args) throws Exception {
        String inputXMLfile1 = "/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/G5ME2G.00007.ME.8402021090712550000_4mb.xml";
        String inputXMLfile2 = "/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/G5ME2G.00007.ME.8402021090712550000_4mb.xml";
        System.out.println(compareXML(inputXMLfile1, inputXMLfile2));
    }

    public static boolean compareXML(String filePath1, String filePath2) throws Exception {
        // Read the first file
        File file1 = new File(filePath1);
        DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
        dbFactory1.setIgnoringElementContentWhitespace(true);
        DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
        Document doc1 = dBuilder1.parse(file1);

        // Read the second file
        File file2 = new File(filePath2);
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

        // Output
        // Output the normalized XML strings
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        StringWriter writer1 = new StringWriter();
        transformer.transform(new DOMSource(doc1), new StreamResult(writer1));
        String normalizedXML1 = writer1.getBuffer().toString();

        StringWriter writer2 = new StringWriter();
        transformer.transform(new DOMSource(doc2), new StreamResult(writer2));
        String normalizedXML2 = writer2.getBuffer().toString();

        // Compare the two normalized XML strings
        return normalizedXML1.equals(normalizedXML2);
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
            attributes.add((Attr) element.getAttributes().item(i));
        }
        Collections.sort(attributes, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        for (Attr attr : attributes) {
            element.setAttribute(attr.getName(), attr.getValue());
        }
    }

    public static String linearizeXML(String filePath) throws Exception {
        // Read the XML file
        File file = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        // Output the linearized XML string
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

    public static void writeXMLtoFile(String linearizedXML, String outputFile) throws Exception {
        // Write the linearized XML string to the new file
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(linearizedXML);
        fileWriter.flush();
        fileWriter.close();
    }
}