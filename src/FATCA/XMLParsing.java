package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParsing {
    public static void main(String[] args) {
        try {
            File file = new File("D:\\xmlFiles\\JP\\JP2016USR5TS2T.00307.SG.230.20160831180224001.xml");

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("ftc:AccountReport");
            if (doc.hasChildNodes()) {
                getNode(nodeList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void getNode(NodeList nodelist) {
        for (int counter = 0; counter < nodelist.getLength(); counter++) {
            Node nodeTemp = nodelist.item(counter);
            if (nodeTemp.getNodeType() == Node.ELEMENT_NODE) {
                if (nodeTemp.getChildNodes().getLength() != 1){
                    System.out.println(nodeTemp.getNodeName() + "[OPEN]");
                }else{
                    System.out.println(nodeTemp.getNodeName() + " : element value = " + nodeTemp.getTextContent() + "[OPEN]");
                }
                if (nodeTemp.hasAttributes()) {
                    NamedNodeMap nodeMap = nodeTemp.getAttributes();
                    for (int mapCounter = 0; mapCounter < nodeMap.getLength(); mapCounter++) {
                        Node nodeTempMap = nodeMap.item(mapCounter);
                        System.out.println("attr name : " + nodeTempMap.getNodeName());
                        System.out.println("attr value : " + nodeTempMap.getNodeValue());
                    }
                }
                if (nodeTemp.hasChildNodes()) {
                    getNode(nodeTemp.getChildNodes());
                }
                System.out.println(nodeTemp.getNodeName() + "[CLOSE]");
            }
        }
    }
}
