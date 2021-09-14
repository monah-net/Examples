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

        System.out.println("Result of validation : " + validateXMLSchema("U:\\XMLSchema\\Standard\\CRS\\2_0\\CrsXML_v2.0.xsd", "C:\\Users\\osolodovnikov\\workingdir\\xmlFiles\\MX2021MXABC821231AC7.20210609111400001.xml"));

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