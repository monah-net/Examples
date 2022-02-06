package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class XMLtoTXTParsing {
    static ArrayList<String> listOfElements = new ArrayList<>();
    public static void main(String[] args) {
        parseXML("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml");
    }
    private static void parseXML(String xmlFilePath){
        try {
            File file = new File(xmlFilePath);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nodeList = doc.getChildNodes();
            if(nodeList.getLength() == 0){
                System.out.println("Check tag name");
            }
            System.out.println(nodeList.getLength());
            if(doc.hasChildNodes()){
                getNode(nodeList);
            }
            printWriter(file.getCanonicalPath(),listOfElements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void getNode(NodeList nodelist) {
        for (int counter = 0; counter < nodelist.getLength(); counter++) {
            Node nodeTemp = nodelist.item(counter);
            if (nodeTemp.getNodeType() == Node.ELEMENT_NODE) {
                if (nodeTemp.getChildNodes().getLength() != 1){
                    listOfElements.add(nodeTemp.getNodeName() + "[OPEN]");
                }else{
                    listOfElements.add(nodeTemp.getNodeName() + " : element value = " + nodeTemp.getTextContent() + "[OPEN]");
                }
                if (nodeTemp.hasAttributes()) {
                    NamedNodeMap nodeMap = nodeTemp.getAttributes();
                    for (int mapCounter = 0; mapCounter < nodeMap.getLength(); mapCounter++) {
                        Node nodeTempMap = nodeMap.item(mapCounter);
                        listOfElements.add("attr name : " + nodeTempMap.getNodeName());
                        listOfElements.add("attr value : " + nodeTempMap.getNodeValue());
                    }
                }
                if (nodeTemp.hasChildNodes()) {
                    getNode(nodeTemp.getChildNodes());
                }
                listOfElements.add(nodeTemp.getNodeName() + "[CLOSE]");
            }
        }
    }
    private static void printWriter (String filename, ArrayList <String> content){
        try (
                PrintWriter writer = new PrintWriter(new File(filename.replaceAll(".xml","RESULT.txt"))) ) {
            for (int counter = 0; counter < content.size(); counter++) {
                writer.write(content.get(counter) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}