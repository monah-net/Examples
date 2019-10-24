package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLComparator {
    public static void main(String[] args) {
        try {

            File file = new File("D:\\xmlFiles\\JP\\JP2016USR5TS2T.00307.SG.230.20160831180224001.xml");
            File file2 = new File("D:\\xmlFiles\\JP\\JP2016USR5TS2T.00307.SG.230.20160831180224051ORACLE.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            DocumentBuilder dBuilder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Document doc2 = dBuilder2.parse(file2);
            NodeList nodelist = doc.getElementsByTagName("ftc:AccountReport");
            NodeList nodeList2 = doc2.getElementsByTagName("ftc:AccountReport");
            Node node = nodelist.item(10);
            Node node1 = nodeList2.item(10);
            System.out.println(node.getChildNodes().item(1).getChildNodes().item(1).getNextSibling().getTextContent());
            System.out.println(node.getNodeType() == Node.ELEMENT_NODE);
            System.out.println(node1.getNodeType() == Node.ELEMENT_NODE);
            System.out.println(node.getTextContent().equals(node1.getTextContent()));
            for (int counter = 0; counter < nodelist.getLength(); counter++) {
                if (nodelist.item(counter).getNodeType() == Node.ELEMENT_NODE) {
                    for (int counter2 = 0; counter2 < nodeList2.getLength(); counter2++) {
                        if (nodeList2.item(counter2).getNodeType() == Node.ELEMENT_NODE) {
                            if (nodelist.item(counter).getTextContent().replaceAll("\\<DocRefId\\>[0-9A-Za-z\\.]*\\<\\/ftc:DocRefId\\>", "").trim().equals(nodeList2.item(counter2).getTextContent().replaceAll("\\<DocRefId\\>[0-9A-Za-z\\.]*\\<\\/ftc:DocRefId\\>", "").trim())) {
                                System.out.println("Account number" + counter);
                                System.out.println(counter2);
                                System.out.println(nodelist.item(counter).getTextContent().replaceAll("\\<DocRefId\\>[0-9A-Za-z\\.]*\\<\\/ftc:DocRefId\\>", "").trim());
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
