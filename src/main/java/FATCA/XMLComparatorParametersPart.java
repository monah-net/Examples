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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLComparatorParametersPart {
    private static String  SUBOWNER = "ControllingPerson";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        File inputXmlFile = new File("U:\\XMLSchema\\xml_files\\tst_case1\\G5ME2G.00007.ME.8402021060708180000_CP_Linearised.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList allElementsIn = javaParsedInput.getElementsByTagName("*");
        Map<String, List<String>> params = new HashMap<String, List<String>>();
//        Map<String, ArrayList<ArrayList<String>>> paramsTEST = new HashMap<String, ArrayList<ArrayList<String>>>();
//        paramsTEST.put("ControllingPerson",new ArrayList<ArrayList<String>>(Arrays.asList("Person", "HolderTaxInfo","TIN"),Arrays.asList("Person", "HolderTaxInfo","ResCountryCode")));
        params.put("ControllingPerson", new ArrayList<>(Arrays.asList("Person", "HolderTaxInfo")));
        Map<String, String> iteratebleElements = new HashMap<String, String>();
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
                                System.out.println(subNode.getTextContent());
                                System.out.println(subNode.getChildNodes().getLength());
                                System.out.println(subNode.getFirstChild().getNodeType() == Node.TEXT_NODE);
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(key);
    }
}
