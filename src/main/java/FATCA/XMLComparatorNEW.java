package FATCA;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.apache.commons.codec.binary.StringUtils;
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
import java.util.*;
import java.util.stream.Collectors;

class XML_comparatorNEW {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ArrayList resElementsIn = new ArrayList<String>();
        ArrayList resElementsOut = new ArrayList<String>();
        File inputXmlFile = new File("/Users/olegsolodovnikov/Desktop/XML_Files/id001/JP2016USR5TS2T.00307.SG.230.20210625123100001.xml");
        File outputXmlFile = new File("/Users/olegsolodovnikov/Desktop/XML_Files/id001/JP2016USR5TS2T.00307.SG.230.20210625123100001.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        Document javaParsedOutput = dBuilderInput.parse(outputXmlFile);
        NodeList allElements = javaParsedInput.getElementsByTagName("*");
        NodeList allElementsOut = javaParsedOutput.getElementsByTagName("*");
        System.out.println(allElements.getLength());
        System.out.println(allElementsOut.getLength());
//        get all elements from file into resElementsIn array
        for (int i = 0; i < allElements.getLength(); i++) {
            Node current = allElements.item(i);
            Node currChild = current.getFirstChild();
            if (currChild.getNodeType() == Node.TEXT_NODE) {
                StringBuilder attribute = new StringBuilder();
                if (current.hasAttributes()) {
                    NamedNodeMap currAttrs = current.getAttributes();
                    List currAttrTempList = new ArrayList<String>();
                    for (int j = 0; j < currAttrs.getLength(); j++) {
                        currAttrTempList.add("/" + currAttrs.item(j).getNodeName() + "=" + currAttrs.item(j).getNodeValue());
                    }
                    Collections.sort(currAttrTempList);
                    for (int k = 0; k < currAttrTempList.size(); k++) {
                        attribute.append(currAttrTempList.get(k));
                    }
                }
                String currElemName = current.getNodeName() + attribute + ":" + current.getTextContent();
                while (!current.getParentNode().getNodeName().equals("#document")) {
                    current = current.getParentNode();
                    StringBuilder attributeParent = new StringBuilder();
                    if (current.hasAttributes() && !current.getParentNode().getNodeName().equals("#document")) {
                        NamedNodeMap currAttrs = current.getAttributes();
                        List attrTempList = new ArrayList<>();
                        for (int j = 0; j < currAttrs.getLength(); j++) {
                            attrTempList.add("/" + currAttrs.item(j).getNodeName() + "=" + currAttrs.item(j).getNodeValue());
                        }
                        Collections.sort(attrTempList);
                        for (int j = 0; j < attrTempList.size(); j++) {
                            attributeParent.append(attrTempList.get(j));
                        }
                    }
                    currElemName = current.getNodeName() + attributeParent + "." + currElemName;
                }
                resElementsIn.add(currElemName);
            }
        }
        //        get all elements from file into resElementsOut array
        for (int i = 0; i < allElementsOut.getLength(); i++) {
            Node current = allElementsOut.item(i);
            Node currChild = current.getFirstChild();
            if (currChild.getNodeType() == Node.TEXT_NODE) {
                StringBuilder attribute = new StringBuilder();
                if (current.hasAttributes()) {
                    NamedNodeMap currAttrs = current.getAttributes();
                    List currAttrTempList = new ArrayList<>();
                    for (int j = 0; j < currAttrs.getLength(); j++) {
                        currAttrTempList.add("/" + currAttrs.item(j).getNodeName() + "=" + currAttrs.item(j).getNodeValue());
                    }
                    Collections.sort(currAttrTempList);
                    for (int k = 0; k < currAttrTempList.size(); k++) {
                        attribute.append(currAttrTempList.get(k));
                    }
                }
                String currElemName = current.getNodeName() + attribute + ":" + current.getTextContent();
                while (!current.getParentNode().getNodeName().equals("#document")) {
                    current = current.getParentNode();
                    StringBuilder attributeParent = new StringBuilder();
                    if (current.hasAttributes() && !current.getParentNode().getNodeName().equals("#document")) {
                        NamedNodeMap currAttrs = current.getAttributes();
                        List attrTempList = new ArrayList<>();
                        for (int j = 0; j < currAttrs.getLength(); j++) {
                            attrTempList.add("/" + currAttrs.item(j).getNodeName() + "=" + currAttrs.item(j).getNodeValue());
                        }
                        Collections.sort(attrTempList);
                        for (int j = 0; j < attrTempList.size(); j++) {
                            attributeParent.append(attrTempList.get(j));
                        }
                    }
                    currElemName = current.getNodeName() + attributeParent + "." + currElemName;
                }
                resElementsOut.add(currElemName);
            }
        }
        System.out.println(resElementsIn);
        System.out.println(resElementsIn.size());
        System.out.println(resElementsOut);
        System.out.println(resElementsOut.size());
//        Compairing arrays
        List resElementsInTemp = new ArrayList();
        List resElementsOutTemp = new ArrayList();
        resElementsInTemp.addAll(resElementsIn);
        resElementsOutTemp.addAll(resElementsOut);
        System.out.println(resElementsInTemp);
        System.out.println(resElementsOutTemp);
        resElementsIn.removeAll(resElementsOutTemp);
        resElementsOut.removeAll(resElementsInTemp);
        System.out.println(resElementsIn);
        System.out.println(resElementsOut);
        System.out.println(resElementsIn.isEmpty());
        System.out.println(resElementsOut.isEmpty());
        StringUtils.equals("000713141414","00071311141414");
        System.out.println(StringUtils.equals("000713141414","00071311141414"));
    }
}