package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XMLComparatorParametersPart {
    private static String SUBOWNER = "ControllingPerson";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        File inputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_Lineriased.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList allElementsIn = javaParsedInput.getElementsByTagName("*");
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put("ControllingPerson", new ArrayList<String>(Arrays.asList("Person", "HolderTaxInfo", "TIN")));
//        System.out.println(params.get("ControllingPerson").get(2));
        String key = null;
        for (int i = 0; i < allElementsIn.getLength(); i++) {
            Node currentNode = allElementsIn.item(i);
            if (currentNode.getNodeName() == SUBOWNER) {
                NodeList list = currentNode.getChildNodes();
                for (int j = 0; j < list.getLength(); j++) {
                    if (currentNode.getFirstChild().getNodeName() == params.get(SUBOWNER).get(0)) {
                        currentNode = currentNode.getFirstChild();
                        System.out.println("Person is here!!!");
                        NodeList personList = currentNode.getChildNodes();
                        for (int k = 0; k < personList.getLength(); k++) {
                            currentNode = personList.item(k);
                            if(currentNode.getNodeType() == Node.ELEMENT_NODE) {
                                if (personList.item(k).getNodeName() == params.get(SUBOWNER).get(1)) {
                                    currentNode = personList.item(k);
                                    System.out.println("Tax info is here");
                                    if (personList.item(k).getNodeName() == params.get(SUBOWNER).get(2)){
                                        key = currentNode.getTextContent();
                                        break;
                                    }
                                } else {
                                    currentNode = currentNode.getNextSibling();
                                }
                            } else {
                                k++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(key);
    }
}
