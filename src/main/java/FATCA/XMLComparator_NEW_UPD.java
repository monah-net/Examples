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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XMLComparator_NEW_UPD {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        //get all elements, that have more than one the same child elements
        File inputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList allElementsIn = javaParsedInput.getElementsByTagName("*");
        Map <String, Set<String>> manyChildren = new HashMap<>();
        for (int i = 0; i < allElementsIn.getLength(); i++) {
            Node currNode = allElementsIn.item(i);
            String currNodeName = currNode.getNodeName();
            if(currNode.hasChildNodes()){
                Set<String> elemChildrenNames= new HashSet<>();
                Set<String> elemChildrenNamesRepeated = manyChildren.get(currNodeName);
                NodeList allNodeChildren = currNode.getChildNodes();
                for (int j = 0; j < allNodeChildren.getLength(); j++) {
                    String childName = allNodeChildren.item(j).getNodeName();
                    if (elemChildrenNames.contains(childName)){
                        if (elemChildrenNamesRepeated == null){
                            elemChildrenNamesRepeated = new HashSet<>();
                            manyChildren.put(currNodeName, elemChildrenNamesRepeated);
//                            System.out.println(manyChildren);
                        }
                        elemChildrenNamesRepeated.add(childName);
                    }
                    elemChildrenNames.add(childName);
                }
            }
        }
        System.out.println(manyChildren);
    }
}
