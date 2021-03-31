package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DOMTestElement {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File inputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/Axiom/FATCA/Tickets/XMLFilesComparison/MRID1234500120201127_Alternative.xml");
        File outputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/Axiom/FATCA/Tickets/XMLFilesComparison/MRID1234500120201127.xml");
        String elementName = "crs:DocSpec";
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList nodelistInput = javaParsedInput.getElementsByTagName(elementName);
        for (int accRepCounterLvl1 = 0; accRepCounterLvl1 < nodelistInput.getLength(); accRepCounterLvl1++) {//<for>
            if (nodelistInput.item(accRepCounterLvl1).getNodeType() == Node.ELEMENT_NODE) {
                Node accRepElementLvl1 = nodelistInput.item(accRepCounterLvl1);
                NodeList elementChildsLvl1 = accRepElementLvl1.getChildNodes();
                String elemNameLvl1 = accRepElementLvl1.getNodeName() + ".";
                if (accRepElementLvl1.hasAttributes()) {
                    NamedNodeMap nodeMap = accRepElementLvl1.getAttributes();
                    ArrayList<String> attrListLvl1 = new ArrayList<>();
                    for (int accRepElemAttrsLvl1 = 0; accRepElemAttrsLvl1 < nodeMap.getLength(); accRepElemAttrsLvl1++) {
                        Node nodeElemAttrsLvl1 = nodeMap.item(accRepElemAttrsLvl1);
                        String temp = "attr name : " + nodeElemAttrsLvl1.getNodeName();
                        temp = temp + "attr value : " + nodeElemAttrsLvl1.getNodeValue();
                        attrListLvl1.add(temp);
                    }
                    Collections.sort(attrListLvl1);
                    for (int accRepElemAttrsOutPutLvl1 = 0; accRepElemAttrsOutPutLvl1 < attrListLvl1.size(); accRepElemAttrsOutPutLvl1++) {
                        elemNameLvl1 = elemNameLvl1 + " " + attrListLvl1.get(accRepElemAttrsOutPutLvl1);
                    }
                }
                if (elementChildsLvl1.getLength() == 1) {
                    elemNameLvl1 = elemNameLvl1 + " text: " + accRepElementLvl1.getTextContent();
                    System.out.println(elemNameLvl1);
                } else {
                    for (int elemCounterLvl2 = 0; elemCounterLvl2 < elementChildsLvl1.getLength(); elemCounterLvl2++) { //Acc report elements
                        if (nodelistInput.item(elemCounterLvl2).getNodeType() == Node.ELEMENT_NODE) {
                            Node subElementLvl1 = elementChildsLvl1.item(elemCounterLvl2);
                            NodeList subElementChildsLvl1 = subElementLvl1.getChildNodes();
                            String elemNameLvl2 = subElementLvl1.getNodeName() + ".";
                            if (subElementLvl1.hasAttributes()) {
                                NamedNodeMap nodeMap = subElementLvl1.getAttributes();
                                ArrayList<String> attrListLvl2 = new ArrayList<>();
                                for (int accRepElemAttrsLvl2 = 0; accRepElemAttrsLvl2 < nodeMap.getLength(); accRepElemAttrsLvl2++) {
                                    Node nodeElemAttrsLvl2 = nodeMap.item(accRepElemAttrsLvl2);
                                    String temp = "attr name : " + nodeElemAttrsLvl2.getNodeName();
                                    temp = temp + "attr value : " + nodeElemAttrsLvl2.getNodeValue();
                                    attrListLvl2.add(temp);
                                }
                                Collections.sort(attrListLvl2);
                                for (int accRepElemAttrsOutPutLvl1 = 0; accRepElemAttrsOutPutLvl1 < attrListLvl2.size(); accRepElemAttrsOutPutLvl1++) {
                                    elemNameLvl2 = elemNameLvl2 + " " + attrListLvl2.get(accRepElemAttrsOutPutLvl1);
                                }
                            }
                            if (elementChildsLvl1.getLength() == 1) {
                                elemNameLvl2 = elemNameLvl2 + " text: " + subElementLvl1.getTextContent();
                                System.out.println(elemNameLvl2);
                            } else {
                                System.out.println(elemNameLvl2);
                                System.out.println("make all steps again");
                            }
                        }
                    }
                }
            }
        }
    }
}
