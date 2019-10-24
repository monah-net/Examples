package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
<<<<<<< HEAD

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
=======
import java.io.PrintWriter;
import java.util.ArrayList;

public class XMLParsing {
    static ArrayList<String> listOfElements = new ArrayList<>();
    static ArrayList<String> listOfElements2 = new ArrayList<>();
    public static void main(String[] args) {
        try {

            File file = new File("/Users/olegsolodovnikov/Desktop/Files/xmlFilesForTesting/R5TS2T.00307.SG.2302016083118022401.xml");

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
            printWriter("/Users/olegsolodovnikov/Desktop/Files/xmlFilesForTesting/R5TS2T.00307.SG.2302016083118022401OUT2.txt",listOfElements);
>>>>>>> origin/master
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
<<<<<<< HEAD
    private static void getNode(NodeList nodelist) {
        for (int counter = 0; counter < nodelist.getLength(); counter++) {
            Node nodeTemp = nodelist.item(counter);
            if (nodeTemp.getNodeType() == Node.ELEMENT_NODE) {
                if (nodeTemp.getChildNodes().getLength() != 1){
                    System.out.println(nodeTemp.getNodeName() + "[OPEN]");
                }else{
                    System.out.println(nodeTemp.getNodeName() + " : element value = " + nodeTemp.getTextContent() + "[OPEN]");
=======

    private static void getNode(NodeList nodelist) {
        for (int counter = 0; counter < nodelist.getLength(); counter++) {
            Node nodeTemp = nodelist.item(counter);
            System.out.println(nodeTemp.getNodeName());
            System.out.println(nodeTemp.getNodeName().matches("ftc:AccountReport"));
            if (nodeTemp.getNodeType() == Node.ELEMENT_NODE) {
                if (nodeTemp.getChildNodes().getLength() != 1){
                    listOfElements.add(nodeTemp.getNodeName() + "[OPEN]");
                }else{
                    listOfElements.add(nodeTemp.getNodeName() + " : element value = " + nodeTemp.getTextContent() + "[OPEN]");
>>>>>>> origin/master
                }
                if (nodeTemp.hasAttributes()) {
                    NamedNodeMap nodeMap = nodeTemp.getAttributes();
                    for (int mapCounter = 0; mapCounter < nodeMap.getLength(); mapCounter++) {
                        Node nodeTempMap = nodeMap.item(mapCounter);
<<<<<<< HEAD
                        System.out.println("attr name : " + nodeTempMap.getNodeName());
                        System.out.println("attr value : " + nodeTempMap.getNodeValue());
=======
                        listOfElements.add("attr name : " + nodeTempMap.getNodeName());
                        listOfElements.add("attr value : " + nodeTempMap.getNodeValue());
>>>>>>> origin/master
                    }
                }
                if (nodeTemp.hasChildNodes()) {
                    getNode(nodeTemp.getChildNodes());
                }
<<<<<<< HEAD
                System.out.println(nodeTemp.getNodeName() + "[CLOSE]");
            }
        }
    }
=======
                listOfElements.add(nodeTemp.getNodeName() + "[CLOSE]");
            }
        }
    }
    private static void printWriter (String filename, ArrayList <String> content){
        try (
                PrintWriter writer = new PrintWriter(new File(filename)) ) {
            for (int counter = 0; counter < content.size(); counter++) {
                writer.write(content.get(counter) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
>>>>>>> origin/master
}
