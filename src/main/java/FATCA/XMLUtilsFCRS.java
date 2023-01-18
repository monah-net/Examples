package FATCA;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class XMLUtilsFCRS {
    public static void main(String[] args) throws Exception {
/*        String inputXMLfile1 = "/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_Lineriased.xml";
        String inputXMLfile2 = "/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_LineriasedUPD.xml";
        System.out.println(compareXML(inputXMLfile1, inputXMLfile2));
        writeXMLtoFile(linearizeXML("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\G5ME2G.00007.ME.840202109072100000032Mb.xml"), "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\G5ME2G.00007.ME.840202109072100000032UPDMb.xml");
        System.out.println(isWellFormed("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_acc_payment_difference\\origin_GB_crs_LineriasedUPD.xml"));*/
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case7_UK_valid\\origin_GB_crs_LineriasedVALID_UK.xml";
        String xmlSchemaPath = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\xsdSchemas\\UK_Schema\\uk_aeoi_submission_v2.0.xsd";
        System.out.println(validateXMLWithSchema(file1Path,xmlSchemaPath));
    }

    /**
     * Compares two XML files and returns true if they are equal, false otherwise.
     *
     * @param originFilePath   the path of the first XML file
     * @param modifiedFilePath the path of the second XML file
     * @return true if the files are equal, false otherwise
     * @throws Exception if there is an error reading or parsing the files, or if the files are not found
     */
    public static boolean compareXML(String originFilePath, String modifiedFilePath) throws Exception {
        // Create a single DocumentBuilderFactory and DocumentBuilder instance
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // Read the first file
        File originFile = new File(originFilePath);
        if (!originFile.exists() || !originFile.isFile()) {
            throw new FileNotFoundException("File not found: " + originFilePath);
        }
        if (originFile.length() == 0) {
            throw new Exception("First file is empty: " + originFilePath);
        }
        Document originDoc;
        try {
            originDoc = dBuilder.parse(originFile);
        } catch (SAXException e) {
            throw new Exception("Error parsing first XML file: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Error reading first XML file: " + e.getMessage());
        }

        // Read the second file
        File modifiedFile = new File(modifiedFilePath);
        if (!modifiedFile.exists() || !modifiedFile.isFile()) {
            throw new FileNotFoundException("File not found: " + modifiedFilePath);
        }
        if (modifiedFile.length() == 0) {
            throw new Exception("Second file is empty: " + modifiedFilePath);
        }
        Document modifiedDoc;
        try {
            modifiedDoc = dBuilder.parse(modifiedFile);
        } catch (SAXException e) {
            throw new Exception("Error parsing second XML file: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Error reading second XML file: " + e.getMessage());
        }

        // Sort the elements
        sortElements(originDoc);
        sortElements(modifiedDoc);

        // Sort the attributes for each element
        sortAttributes(originDoc);
        sortAttributes(modifiedDoc);

        // Output the normalized XML strings
        StringWriter writer1 = new StringWriter();
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        XMLStreamWriter xtw = xof.createXMLStreamWriter(writer1);
        xtw.writeStartDocument();
        writeNode(originDoc.getDocumentElement(), xtw);
        xtw.writeEndDocument();
        xtw.flush();
        xtw.close();
        String normalizedXML1 = writer1.toString();

        StringWriter writer2 = new StringWriter();
        xof = XMLOutputFactory.newInstance();
        xtw = xof.createXMLStreamWriter(writer2);
        xtw.writeStartDocument();
        writeNode(modifiedDoc.getDocumentElement(), xtw);
        xtw.writeEndDocument();
        xtw.flush();
        xtw.close();
        String normalizedXML2 = writer2.toString();

        // Compare the two normalized XML strings
        return normalizedXML1.equals(normalizedXML2);
    }

    /**
     * Recursively writes an XML document using the XMLStreamWriter class.
     *
     * @param node a node object from the XML document.
     * @param xtw  an instance of the XMLStreamWriter class that is used to write the XML document.
     * @throws Exception if there is an error writing the XML document.
     */
    private static void writeNode(Node node, XMLStreamWriter xtw) throws Exception {
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                xtw.writeStartElement(node.getNodeName());
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    writeNode(children.item(i), xtw);
                }
                xtw.writeEndElement();
                break;
            case Node.TEXT_NODE:
                xtw.writeCharacters(node.getTextContent());
                break;
            default:
                break;
        }
    }

    /**
     * Sorts the child elements of the given element in alphabetical order.
     * This method is called recursively for all child elements, ensuring that
     * the entire element tree is sorted.
     */
    private static void sortElements(Document doc) {
        NodeList nodes = doc.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            sortChildren(element);
        }
    }

    /**
     * Recursively sorts the children of the given element by their tag name.
     * This method will sort all child elements of the given element, including their own children.
     */
    private static void sortChildren(Element element) {
        List<Element> children = new ArrayList<>();
        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                children.add((Element) node);
            }
        }
        children.sort(Comparator.comparing(Element::getTagName));
        for (Element child : children) {
            sortChildren(child);
            element.appendChild(child);
        }
    }

    /**
     * Sorts the attributes of each element in the given document in alphabetical order by attribute name.
     *
     * @param doc The Document object to sort attributes for.
     */
    private static void sortAttributes(Document doc) {
        NodeList nodes = doc.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            sortAttribs(element);
        }
    }

    /**
     * Sorts the attributes of the given element in alphabetical order by attribute name.
     *
     * @param element The element whose attributes should be sorted.
     */
    private static void sortAttribs(Element element) {
        List<Attr> attributes = new ArrayList<>();
        for (int i = 0; i < element.getAttributes().getLength(); i++) {
            attributes.add((Attr) element.getAttributes().item(i));
        }
        attributes.sort(Comparator.comparing(Attr::getName));
        for (Attr attr : attributes) {
            element.setAttribute(attr.getName(), attr.getValue());
        }
    }

    /**
     * The method linearizeXML takes a file path of an XML file as an input and returns a linearized XML string.
     * The method uses the DocumentBuilderFactory, DocumentBuilder, TransformerFactory, and Transformer classes to read the XML file and output the linearized string.
     *
     * @param filePath a string representing the path to the XML file
     * @return a string representing the linearized XML
     * @throws Exception if there is an error reading or parsing the XML file, or if an error occurs while linearizing the XML
     */
    private static String linearizeXML(String filePath) throws Exception {
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
        return writer.getBuffer().toString().replaceAll(">\\s*\n\\s*<", "><");
    }

    /**
     * Writes the given XML string to a new file at the specified path.
     *
     * @param inputXMLString The XML string to write to the file.
     * @param outputFile     The path of the new file to be created.
     * @throws Exception if an error occurs while writing to the file.
     */
    private static void writeXMLtoFile(String inputXMLString, String outputFile) throws Exception {
        // Write the linearized XML string to the new file
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(inputXMLString);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This class provides a method for validating an XML file against a schema
     *
     * @param xmlFilePath    path to the XML file
     * @param schemaFilePath path to the XSD schema file
     * @return a list of error messages, or an empty list if the XML file is valid
     * @throws SAXException if an error occurs while parsing the schema
     * @throws IOException  if an error occurs while reading the XML file
     */
    public static List<String> validateXMLWithSchema(String xmlFilePath, String schemaFilePath) throws Exception {
        List<String> errors = new ArrayList<>();
        // Create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // Load the schema
        File schemaFile = new File(schemaFilePath);
        Schema schema = factory.newSchema(schemaFile);

        // Create a Validator object, which can be used to validate an instance document
        Validator validator = schema.newValidator();
        // Register a custom error handler
        validator.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                errors.add(exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                errors.add(exception.getMessage());
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                errors.add(exception.getMessage());
            }
        });

        // Validate the XML file against the schema
        File xmlFile = new File(xmlFilePath);
        validator.validate(new StreamSource(xmlFile));

        return errors;
    }

    public static String replaceInTags(String inputFilePath, String oldChars, String newChars) {
        StringBuilder output = new StringBuilder();
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            output.append("Error: input file does not exist");
            return output.toString();
        }
        String[] oldCharsArray = oldChars.split("\\|");
        String[] newCharsArray = newChars.split("\\|");
        if (oldCharsArray.length != newCharsArray.length) {
            output.append("Error: oldChars and newChars must have the same number of delimiters");
            return output.toString();
        }
        boolean inTag = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            int c;
            while ((c = reader.read()) != -1) {
                char ch = (char) c;
                // Check if current character is '<', if true then set 'inTag' flag to true
                if (ch == '<') {
                    inTag = true;
                }
                // Check if current character is '>', if true then set 'inTag' flag to false
                else if (ch == '>') {
                    inTag = false;
                }
                // Check if current character is in oldChars and 'inTag' flag is false, if true then replace it with newChars
                else if (!inTag) {
                    for (int i = 0; i < oldCharsArray.length; i++) {
                        if (ch == oldCharsArray[i].charAt(0)) {
                            ch = newCharsArray[i].charAt(0);
                            break;
                        }
                    }
                }
                // Append the current character to output
                output.append(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return output String
        return output.toString();
    }

    public static boolean isWellFormed(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(new File(filePath));
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return false;
        }
    }
}