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

public class XMLComparatorGetAllIterableElements {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        File inputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList allElementsIn = javaParsedInput.getElementsByTagName("*");
        Map<String, String> manyChildren = new HashMap<String, String>();
        manyChildren.put("Person","HolderTaxInfo");
        for (int mainCounter = 0; mainCounter < allElementsIn.getLength(); mainCounter++) {
            if (allElementsIn.item(mainCounter).getNodeType() == Node.ELEMENT_NODE) {
                Node currentNode = allElementsIn.item(mainCounter);
                List<String> listManyChildren = new ArrayList<>();
                if(currentNode.getNodeName() == manyChildren.get("Person")){
                    StringBuilder manyChildrenPath = new StringBuilder();
                    NodeList manyChildrenList = currentNode.getChildNodes();
                    for (int manyChildrenCounter = 0; manyChildrenCounter < manyChildrenList.getLength(); manyChildrenCounter++) {
                        Node childTemp = manyChildrenList.item(manyChildrenCounter);
                        if (childTemp.getNodeType() == Node.ELEMENT_NODE){
                            if(childTemp.getNodeType() == Node.TEXT_NODE){
                                manyChildrenPath.append(childTemp.getNodeType());
                                manyChildrenPath.append(childTemp.getTextContent());
                            } else {
                                manyChildrenPath.append(childTemp.getNodeName());
                            }
                            listManyChildren.add(manyChildrenPath.toString());
                        }
                    }
                }
                System.out.println(listManyChildren);
            }
        }
    }
}
