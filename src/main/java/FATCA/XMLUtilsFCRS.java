package FATCA;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class XMLUtilsFCRS {
    public static void main(String[] args) {
        String filetest1 = "/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case11_replace_chars1/12345678902021112311270000ORIGINAL.xml";
        System.out.println(replaceInTags(filetest1,"*|#|!","T|TE|TEST"));

    }

    /**
     * Compares two XML files and returns true if they are equal, false otherwise.
     *
     * @param originFilePath   the path of the first XML file
     * @param modifiedFilePath the path of the second XML file
     * @return true if the files are equal, false otherwise
     * @throws Exception if there is an error reading or parsing the files, or if the files are not found
     */
    public static boolean compareXMLFilesBoolean(String originFilePath, String modifiedFilePath) throws Exception {
        boolean compareXMLResultBool = true;
        //Linearize input xml's and save as with additional text Temp in their names
        String originFilePathTemp = originFilePath.replace(".xml", "Temp.xml");
        String modifiedFilePathTemp = modifiedFilePath.replace(".xml", "Temp.xml");

        linearizeXML(originFilePath, originFilePathTemp);
        linearizeXML(modifiedFilePath, modifiedFilePathTemp);

        // Read the origin file
        Document originDoc = readXMLFileToDoc(originFilePath);
        // Read the modified file
        Document modifiedDoc = readXMLFileToDoc(modifiedFilePath);

        // Sort the elements
        sortElements(originDoc);
        sortElements(modifiedDoc);

        // Sort the attributes for each element
        sortAttributes(originDoc);
        sortAttributes(modifiedDoc);

        // Output the normalized XML strings
        String originDocNormalizedXML = normalizeXML(originDoc);
        String modifiedDocNormalizedXML = normalizeXML(modifiedDoc);
        // Deletion temp files
        deleteFile(originFilePathTemp);
        deleteFile(modifiedFilePathTemp);

        // Compare the two normalized XML strings
        return originDocNormalizedXML.equals(modifiedDocNormalizedXML);
    }

    /**
     * Reads an XML file from the specified file path and returns a org.w3c.dom.Document object.
     *
     * @param filePath the path of the file to be read.
     * @return a org.w3c.dom.Document object representing the XML file.
     * @throws Exception if the file is not found, is empty, or an error occurs while parsing or reading the file.
     */
    private static Document readXMLFileToDoc(String filePath) throws Exception {
        // Create a File object for the file to be read
        File file = new File(filePath);
        // Check if the file exists and is a file
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        // Check if the file is empty
        if (file.length() == 0) {
            throw new Exception("File is empty: " + filePath);
        }

        // Create a DocumentBuilderFactory object
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        // Set the factory to ignore whitespace in the XML document
        dbFactory.setIgnoringElementContentWhitespace(true);
        // Create a DocumentBuilder object
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc;
        try {
            // Parse the file and create a Document object
            doc = dBuilder.parse(file);
        } catch (SAXException e) {
            throw new Exception("Error parsing XML file: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Error reading XML file: " + e.getMessage());
        }
        // Return the Document object
        return doc;
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
     * Recursively sorts the children of the given element by their tag name and context.
     * This method will sort all child elements of the given element, including their own children.
     *
     * @param element the element whose children will be sorted.
     */
    private static void sortChildren(Element element) {
        // Create a list to store the children elements
        List<Element> children = new ArrayList<>();
        // Get the list of child nodes of the element
        NodeList nodes = element.getChildNodes();
        // Iterate through the list of child nodes
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            // Check if the current node is an Element
            if (node instanceof Element) {
                children.add((Element) node);
            }
        }
        // Sort the children elements based on their tag name and text content
        children.sort(Comparator.comparing(Element::getTagName).thenComparing(Element::getTextContent));
        // Iterate through the sorted children elements
        for (Element child : children) {
            // Recursively sort the children of this child element
            sortChildren(child);
            // Append the child element back to the original element
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
     * Linearize the XML content in the input file and write the result to the output file.
     *
     * @param inputFilePath  the path of the input file containing the XML content
     * @param outputFilePath the path of the output file to write the linearized XML content
     * @throws Exception if any error occurs while reading, parsing or writing the file.
     */

    private static void linearizeXML(String inputFilePath, String outputFilePath) throws Exception {
        // Read the XML file
        File inputFile = new File(inputFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);

        // Make changes to the document here

        // Output the linearized XML string
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String linearizedXML = writer.getBuffer().toString().replaceAll(">\\s*\n\\s*<", "><");
        // Write the changes to the output file
        File outputFile = new File(outputFilePath);
        transformer.transform(new StreamSource(new StringReader(linearizedXML)), new StreamResult(outputFile));
    }

    /**
     * Normalize the XML content of the given Document object.
     *
     * @param doc the Document object containing the XML content
     * @return a string representing the normalized XML content
     * @throws Exception if any error occurs while normalizing the XML
     */
    private static String normalizeXML(Document doc) throws Exception {
        // Create a StringWriter to write the normalized XML to
        StringWriter writer = new StringWriter();
        // Create an XMLOutputFactory to write the XML
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        // Create an XMLStreamWriter to write the XML to the StringWriter
        XMLStreamWriter xtw = xof.createXMLStreamWriter(writer);
        // Write the start of the document
        xtw.writeStartDocument();
        // Write the document element and all its children
        writeNode(doc.getDocumentElement(), xtw);
        // Write the end of the document
        xtw.writeEndDocument();
        // Flush the writer
        xtw.flush();
        // Close the writer
        xtw.close();
        // Get the normalized XML as a string
        String normalizedXML = writer.toString();
        return normalizedXML;
    }

    /**
     * Delete a file.
     *
     * @param filePath the path of the file to delete
     * @return true if the file was successfully deleted, false otherwise
     */
    private static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // Check if the file exists
        if (file.exists()) {
            // If the file exists, attempt to delete it
            return file.delete();
        } else {
            // If the file does not exist, return false
            return false;
        }
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

    /**
     * Replace characters within tags in the input file with new characters.
     *
     * @param inputFilePath the path of the input file containing the text
     * @param oldChars      a string containing the characters to be replaced, separated by '|'
     * @param newChars      a string containing the characters to replace with, separated by '|'
     * @return a string representing the input file with characters within tags replaced
     */
    public static String replaceInTags(String inputFilePath, String oldChars, String newChars) {
        StringBuilder output = new StringBuilder();
        File inputFile = new File(inputFilePath);
        // check if input file exists
        if (!inputFile.exists()) {
            output.append("Error: input file does not exist");
            return output.toString();
        }
        // split the oldChars and newChars by '|'
        String[] oldCharsArray = oldChars.split("\\|");
        String[] newCharsArray = newChars.split("\\|");
        // check if oldChars and newChars have the same number of delimiters
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

    /**
     * Check if the XML file is well-formed.
     *
     * @param filePath the path of the XML file
     * @return true if the file is well-formed, false otherwise
     */
    public static boolean isWellFormed(String filePath) {
        try {
            // Create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Set the factory to not validate and be namespace aware
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            // Create a new DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parse the file
            builder.parse(new File(filePath));
            // If the file is well-formed, it will be successfully parsed and no exception will be thrown
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // If the file is not well-formed, an exception will be thrown
            return false;
        }
    }
}