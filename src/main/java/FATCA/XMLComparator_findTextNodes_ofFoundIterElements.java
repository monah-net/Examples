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

public class XMLComparator_findTextNodes_ofFoundIterElements {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Set<String>> manyChildren = new HashMap<>();
        manyChildren.put("Person",new HashSet<String>(Collections.singleton("HolderTaxInfo")));
        File inputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList allElementsIn = javaParsedInput.getElementsByTagName("*");
        int counter = 0;
        for (int i = 0; i < allElementsIn.getLength(); i++) {
            Node currNode = allElementsIn.item(i);
            if(currNode.getNodeType() == Node.ELEMENT_NODE && currNode.hasChildNodes()){
                Node subNode = currNode.getFirstChild();
                if(subNode.getNodeType() == Node.ELEMENT_NODE){
                    System.out.println(subNode.getNextSibling().getNodeName());
                }
            }
        }
    }
}
