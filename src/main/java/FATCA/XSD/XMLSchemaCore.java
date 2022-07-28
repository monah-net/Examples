import org.apache.ws.commons.schema.*;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XMLSchemaCore {

    private static XmlSchemaCollection xmlSchemaCollection;
    private static Map<QName, List<XmlSchemaElement>> xsdElements = new HashMap<QName, List<XmlSchemaElement>>();
    private static List<XmlSchemaElement> schemaElements = new ArrayList<XmlSchemaElement>();

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException, UnsupportedEncodingException {
        // Path for the file which is stored within the Resource folder
        String xsdPath = Paths.get(XMLSchemaCore.class.getClassLoader().getResource("test.xsd").toURI()).toFile().getAbsolutePath();
        String filePath = Path.of(xsdPath).toString();

        InputStream is = new FileInputStream(filePath);
        xmlSchemaCollection = new XmlSchemaCollection();

        // Schema contain the complete XSD content which needs to be parsed
        XmlSchema schema = xmlSchemaCollection.read(new StreamSource(is));
        // schema.write(System.out);

        // Get the root element from XSD
        Map.Entry<QName, XmlSchemaElement> entry = schema.getElements().entrySet().iterator().next();
        QName rootElement = entry.getKey();

        // Get all the elements based on the parent element
        XmlSchemaElement childElement = xmlSchemaCollection.getElementByQName(rootElement);

        // Call method to get all the child elements
        getChildElementNames(childElement);

        String element = "" + xsdElements.entrySet().stream().map(e -> e.getKey() + " -- " + String.join(", ", e.getValue().stream().map(v -> v
                .getQName().toString()).collect(Collectors.toList()))).collect(Collectors.toList());

        System.out.println(element);
    }

    // Method to check for the child elements and return list
    private static void getChildElementNames(XmlSchemaElement element) {

        // Get the type of the element
        XmlSchemaType elementType = element != null ? element.getSchemaType() : null;

        // Confirm if the element is of Complex type
        if (elementType instanceof XmlSchemaComplexType) {
            // Get all particles associated with that element Type
            XmlSchemaParticle allParticles = ((XmlSchemaComplexType) elementType).getParticle();

            // Check particle belongs to which type
            if (allParticles instanceof XmlSchemaAny) {
                System.out.println("Any Schema Type");

            } else if (allParticles instanceof XmlSchemaElement) {
                System.out.println("Element Schema Type");

            } else if (allParticles instanceof XmlSchemaSequence) {
                // System.out.println("Sequence Schema Type");
                final XmlSchemaSequence xmlSchemaSequence = (XmlSchemaSequence) allParticles;
                final List<XmlSchemaSequenceMember> items = xmlSchemaSequence.getItems();
                items.forEach((item) -> {
                    XmlSchemaElement itemElements = (XmlSchemaElement) item;
                    schemaElements.add(itemElements);
                    // System.out.println(" Parent : " + element.getQName() + " -- Child : " +
                    // itemElements.getQName());
                    //Call the method to add the current element as child
                    addChild(element.getQName(), itemElements);
                    // Call method recursively to get all subsequent element
                    getChildElementNames(itemElements);
                    schemaElements = new ArrayList<XmlSchemaElement>();
                });

            } else if (allParticles instanceof XmlSchemaGroupRef) {

            }
        }
    }

    // Add child elements based on its parent
    public static void addChild(QName qName, XmlSchemaElement child) {
        List<XmlSchemaElement> values = xsdElements.get(qName);
        if (values == null) {
            values = new ArrayList<XmlSchemaElement>();
        }
        values.add(child);
        xsdElements.put(qName, values);
    }

}