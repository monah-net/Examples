package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XMLComparator {
    public static void main(String[] args) {
        try {
            Map<String , String> resultMap = new HashMap<>();
            File file = new File("C:\\Users\\007\\Desktop\\Files\\xmlFilesForTesting\\R5TS2T.00307.SG.2302016083118022401Test.xml");
            File file2 = new File("C:\\Users\\007\\Desktop\\Files\\xmlFilesForTesting\\R5TS2T.00307.SG.2302016083118022401ORACLETest.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            DocumentBuilder dBuilder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Document doc2 = dBuilder2.parse(file2);
            NodeList nodelist = doc.getElementsByTagName("ftc:AccountReport");
            NodeList nodeList2 = doc2.getElementsByTagName("ftc:AccountReport");
            for (int i = 0; i < nodelist.getLength(); i++) {
                resultMap.put("AccountReportNo" + i,"");
            }
            for (int counter = 0; counter < nodelist.getLength(); counter++) {
                if (nodelist.item(counter).getNodeType() == Node.ELEMENT_NODE) {
                    for (int counter2 = 0; counter2 < nodeList2.getLength(); counter2++) {
                        if (nodeList2.item(counter2).getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println(nodelist.item(counter).equals(nodeList2.item(counter2)));
                            System.out.println(nodelist.item(counter).getTextContent().replaceAll("\n","") + "[CLOSE]");
                            System.out.println(nodeList2.item(counter2).getTextContent().replaceAll("\n","") + "[CLOSE]");
                            if (nodelist.item(counter).getTextContent().replaceAll("\n","").trim().equals(nodeList2.item(counter2).getTextContent().replaceAll("\n","").trim())) {
                                resultMap.replace("AccountReportNo" + counter,"DONE");
                                break;
                            }
                        }
                    }
                }
            }
            for (Map.Entry <String, String> pair : resultMap.entrySet()
                 ) {
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}