package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLComparison {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        String fileName1 = new String("/Users/olegsolodovnikov/Desktop/AEOIFATCA-1324/R5TS2T.00307.SG.2302016083118022401.xml");
            // parse the document
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = docBuilder.parse(new File(fileName1));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc.getNodeName());
        System.out.println(doc.getDocumentElement() + " : " + doc.getNodeType() + " : " + doc.getChildNodes());

    }
}