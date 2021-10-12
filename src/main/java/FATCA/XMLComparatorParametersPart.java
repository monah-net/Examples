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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLComparatorParametersPart {
    private static String SUBOWNER = "ControllingPerson";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        File inputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList allElementsIn = javaParsedInput.getElementsByTagName("*");
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put("ControllingPerson", new ArrayList<String>(Arrays.asList("Person", "HolderTaxInfo", "TIN")));
        Map<String, String> iteratebleElements = new HashMap<String, String>();
        String key = null;
        for (int i = 0; i < allElementsIn.getLength(); i++) {
            if(allElementsIn.item(i).getNodeType() == Node.ELEMENT_NODE){
                Node currentNode = allElementsIn.item(i);
                Node subNode = currentNode;
                List <String> tins = new ArrayList<>();
                if (currentNode.getNodeName() == SUBOWNER) {
                    for (int j = 0; j < params.get(SUBOWNER).size(); j++) {
                        subNode = subNode.getFirstChild();
                        while(true){
                            if (subNode.getNodeName() == params.get(SUBOWNER).get(j)){
                                break;
                            }
                            else{
                                subNode = subNode.getNextSibling();
                                System.out.println(subNode.getNodeName());
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(key);
    }
}
