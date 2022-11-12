package FATCA;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlValidator {
    public static void main(String[] args) {

        System.out.println("Result of validation : " + validateXMLSchema("C:\\Users\\osolodovnikov\\workingdir\\FATCA\\XMLValidator\\All Schemes\\CN_Schema_updated_for PBOC requirements\\CN_Schema.xsd", "C:\\Users\\osolodovnikov\\workingdir\\FATCA\\XMLValidator\\Files\\cams.001.001.02RE123456321654982022P00000001.xml"));

    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
}