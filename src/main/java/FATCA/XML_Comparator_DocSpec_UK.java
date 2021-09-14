package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class XML_Comparator_DocSpec_UK {
    public static void main(String[] args) {
        List<String> allDocRefIdElementsList = new ArrayList<>();
        List<String> listDocSpec = new ArrayList<>();
        File inputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/XML_Files/UK/result_fatca_det_uk.xml");
        File outputXmlFile = new File("/Users/olegsolodovnikov/MyDocuments/XML_Files/UK/origin_fatca_det_uk.xml");
        String elementName = "FIReturnRef";
        String messageHeaderElem = "MessageRef";

        try {
            DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            DocumentBuilder dBuilderOutput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
            Document javaParsedOutput = dBuilderInput.parse(outputXmlFile);
            NodeList nodelistInput = javaParsedInput.getElementsByTagName(elementName);
            NodeList nodelistOutput = javaParsedOutput.getElementsByTagName(elementName);
            NodeList nodelistInputMsgHdr = javaParsedInput.getElementsByTagName(messageHeaderElem);
            NodeList nodelistOutputInputMsgHdr = javaParsedOutput.getElementsByTagName(messageHeaderElem);
            NodeList nodelistInputMsgHdrChilds = nodelistInputMsgHdr.item(0).getChildNodes();
            NodeList nodelistOutputMsgHdrChilds = nodelistOutputInputMsgHdr.item(0).getChildNodes();
            ArrayList<String> nodelistInputMsgHdrChildValues = new ArrayList<>();
            ArrayList<String> nodelistOutputMsgHdrChildValues = new ArrayList<>();
            for (int inpMsgHeadCounter = 0; inpMsgHeadCounter < nodelistInputMsgHdrChilds.getLength(); inpMsgHeadCounter++) {
                nodelistInputMsgHdrChildValues.add(nodelistInputMsgHdrChilds.item(inpMsgHeadCounter).getTextContent());
            }
            for (int outpMsgHeadCounter = 0; outpMsgHeadCounter < nodelistOutputMsgHdrChilds.getLength(); outpMsgHeadCounter++) {
                nodelistOutputMsgHdrChildValues.add(nodelistOutputMsgHdrChilds.item(outpMsgHeadCounter).getTextContent());
            }
            nodelistInputMsgHdrChildValues.removeAll(nodelistOutputMsgHdrChildValues);
            if (nodelistInputMsgHdrChildValues.isEmpty()) {
                System.out.println("All elements from input file are presented at an output file");
            }
            for (int j = 0; j < nodelistInput.getLength(); j++) {//add all DocRefId's to the list
                Node nodeTempDocSpecInput = nodelistInput.item(j);
                allDocRefIdElementsList.add(nodeTempDocSpecInput.getTextContent());
            }
            for (int j = 0; j < allDocRefIdElementsList.size(); j++) { //iterate through all DocRefId elements by list, main task all other operations will be inside every iteration
                Node elementTempDocRefIdInput = nodelistInput.item(j);
                Node elementTempParentInput = elementTempDocRefIdInput.getParentNode().getParentNode();
                for (int k = 0; k < nodelistOutput.getLength(); k++) {
                    Node elementTempDocRefIdOutput = nodelistOutput.item(k);
                    if (elementTempDocRefIdInput.getTextContent().equals(elementTempDocRefIdOutput.getTextContent())) {
                        Node elementTempParentOutput = elementTempDocRefIdOutput.getParentNode().getParentNode();
                        if (elementTempParentInput.getNodeName().equals("###ReportingFI")) {
                            ArrayList<String> repFIElemValuesInput = new ArrayList<>();
                            ArrayList<String> repFIElemValuesOutput = new ArrayList<>();
                            NodeList repFIChildsInput = elementTempParentInput.getChildNodes();
                            for (int d = 0; d < repFIChildsInput.getLength(); d++) {
                                if (repFIChildsInput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (repFIChildsInput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = repFIChildsInput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            temp = " attr name : " + node.getNodeName();
                                            temp = temp + " attr value : " + node.getNodeValue();
                                            repFIElemValuesInput.add(repFIChildsInput.item(d).getTextContent() + temp);
                                        }
                                    } else {
                                        repFIElemValuesInput.add(repFIChildsInput.item(d).getTextContent());
                                    }
                                }
                            }
                            NodeList repFIChildsOutput = elementTempParentOutput.getChildNodes();
                            for (int d = 0; d < repFIChildsOutput.getLength(); d++) {
                                if (repFIChildsOutput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (repFIChildsOutput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = repFIChildsOutput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            temp = " attr name : " + node.getNodeName();
                                            temp = temp + " attr value : " + node.getNodeValue();
                                            repFIElemValuesOutput.add(repFIChildsOutput.item(d).getTextContent() + temp);
                                        }
                                    } else {
                                        repFIElemValuesOutput.add(repFIChildsOutput.item(d).getTextContent());
                                    }
                                }
                            }
                            repFIElemValuesInput.removeAll(repFIElemValuesOutput);
                            if (repFIElemValuesInput.isEmpty()) {
                                System.out.println("Element with DocRefID " + elementTempDocRefIdInput.getTextContent() + " does not have difference");
                            } else {
                                System.out.println("AccountReport element contains differences, check " + elementTempParentInput.getNodeName() + " text : " + elementTempDocRefIdInput.getTextContent());
                            }
                        } else if (elementTempParentInput.getNodeName().equals("crs:Sponsor")) {
                            ArrayList<String> sponsorElemValuesInput = new ArrayList<>();
                            ArrayList<String> sponsorElemValuesOutput = new ArrayList<>();
                            NodeList sponsorChildsInput = elementTempParentInput.getChildNodes();
                            for (int d = 0; d < sponsorChildsInput.getLength(); d++) {
                                if (sponsorChildsInput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (sponsorChildsInput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = sponsorChildsInput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                sponsorElemValuesInput.add(sponsorChildsInput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        sponsorElemValuesInput.add(sponsorChildsInput.item(d).getTextContent());
                                    }
                                }
                            }
                            NodeList sponsorChildsOutput = elementTempParentOutput.getChildNodes();
                            for (int d = 0; d < sponsorChildsOutput.getLength(); d++) {
                                if (sponsorChildsOutput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (sponsorChildsOutput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = sponsorChildsOutput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                sponsorElemValuesOutput.add(sponsorChildsOutput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        sponsorElemValuesOutput.add(sponsorChildsOutput.item(d).getTextContent());
                                    }
                                }
                            }
                            sponsorElemValuesInput.removeAll(sponsorElemValuesOutput);
                            if (sponsorElemValuesInput.isEmpty()) {
                                System.out.println("ReportingFI element is OK!");
                            } else {
                                System.out.println("AccountReport element contains differences, check " + elementTempParentInput.getNodeName() + " text : " + elementTempDocRefIdInput.getTextContent());
                            }

                        } else if (elementTempParentInput.getNodeName().equals("crs:Intermediary")) {
                            ArrayList<String> intermElemValuesInput = new ArrayList<>();
                            ArrayList<String> intermElemValuesOutput = new ArrayList<>();
                            NodeList intermChildsInput = elementTempParentInput.getChildNodes();
                            for (int d = 0; d < intermChildsInput.getLength(); d++) {
                                if (intermChildsInput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (intermChildsInput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = intermChildsInput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                intermElemValuesInput.add(intermChildsInput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        intermElemValuesInput.add(intermChildsInput.item(d).getTextContent());
                                    }
                                }
                            }
                            NodeList intermChildsOutput = elementTempParentOutput.getChildNodes();
                            for (int d = 0; d < intermChildsOutput.getLength(); d++) {
                                if (intermChildsOutput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (intermChildsOutput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = intermChildsOutput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                intermElemValuesOutput.add(intermChildsOutput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        intermElemValuesOutput.add(intermChildsOutput.item(d).getTextContent());
                                    }
                                }
                            }
                            intermElemValuesInput.removeAll(intermElemValuesOutput);
                            if (intermElemValuesInput.isEmpty()) {
                                System.out.println("Intermediary element is OK!");
                            } else {
                                System.out.println("AccountReport element contains differences, check " + elementTempParentInput.getNodeName() + " text : " + elementTempDocRefIdInput.getTextContent());
                            }

                        } else if (elementTempParentInput.getNodeName().equals("AccountData")) {
                            ArrayList<String> accRepElemValuesInput = new ArrayList<>();
                            ArrayList<String> accRepElemValuesOutput = new ArrayList<>();
                            NodeList accRepChildsInput = elementTempParentInput.getChildNodes();
                            for (int d = 0; d < accRepChildsInput.getLength(); d++) {
                                if (accRepChildsInput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (accRepChildsInput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = accRepChildsInput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                accRepElemValuesInput.add(accRepChildsInput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        accRepElemValuesInput.add(accRepChildsInput.item(d).getTextContent());
                                    }
                                }
                            }
                            NodeList accRepChildsOutput = elementTempParentOutput.getChildNodes();
                            for (int d = 0; d < accRepChildsOutput.getLength(); d++) {
                                if (accRepChildsOutput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (accRepChildsOutput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = accRepChildsOutput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                accRepElemValuesOutput.add(accRepChildsOutput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        accRepElemValuesOutput.add(accRepChildsOutput.item(d).getTextContent());
                                    }
                                }
                            }
                            accRepElemValuesInput.removeAll(accRepElemValuesOutput);
                            if (accRepElemValuesInput.isEmpty()) {
                                System.out.println("AccountReport element is OK!");
                            } else {
                                System.out.println("AccountReport element contains differences, check " + elementTempParentInput.getNodeName() + " text : " + elementTempDocRefIdInput.getTextContent());
                            }

                        } else if (elementTempParentInput.getNodeName().equals("crs:PoolReport")) {
                            ArrayList<String> poolRepElemValuesInput = new ArrayList<>();
                            ArrayList<String> poolRepElemValuesOutput = new ArrayList<>();
                            NodeList poolRepChildsInput = elementTempParentInput.getChildNodes();
                            for (int d = 0; d < poolRepChildsInput.getLength(); d++) {
                                if (poolRepChildsInput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (poolRepChildsInput.item(d).hasAttributes()) {
                                        String temp;
                                        NamedNodeMap nodeMap = poolRepChildsInput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                poolRepElemValuesInput.add(poolRepChildsInput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        poolRepElemValuesInput.add(poolRepChildsInput.item(d).getTextContent());
                                    }
                                }
                            }
                            NodeList poolRepChildsOutput = elementTempParentOutput.getChildNodes();
                            for (int d = 0; d < poolRepChildsOutput.getLength(); d++) {
                                if (poolRepChildsOutput.item(d).getNodeType() == Node.ELEMENT_NODE) {
                                    if (poolRepChildsOutput.item(d).hasAttributes()) {
                                        NamedNodeMap nodeMap = poolRepChildsOutput.item(d).getAttributes();
                                        for (int i = 0; i < nodeMap.getLength(); i++) {
                                            Node node = nodeMap.item(i);
                                            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                                                String temp = " attr name : " + node.getNodeName();
                                                temp = temp + " attr value : " + node.getNodeValue();
                                                poolRepElemValuesOutput.add(poolRepChildsOutput.item(d).getTextContent() + temp);
                                            }
                                        }
                                    } else {
                                        poolRepElemValuesOutput.add(poolRepChildsOutput.item(d).getTextContent());
                                    }
                                }
                            }
                            poolRepElemValuesInput.removeAll(poolRepElemValuesOutput);
                            if (poolRepElemValuesInput.isEmpty()) {
                                System.out.println("PoolReport element is OK!");
                            } else {
                                System.out.println("AccountReport element contains differences, check " + elementTempParentInput.getNodeName() + " text : " + elementTempDocRefIdInput.getTextContent());
                            }

                        } else if (elementTempParentInput.getNodeName().equals("crs:DocSpec")) {
                            break; //поменять на break
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
