package FATCA;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
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
        List resElements = new ArrayList();
        File inputXmlFile = new File("C:\\Users\\osolodovnikov\\Desktop\\UK\\origin_fatca_det_uk_TEST_Lineriased.xml");
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document javaParsedInput = dBuilderInput.parse(inputXmlFile);
        NodeList allElements = javaParsedInput.getElementsByTagName("*");
        System.out.println(allElements.getLength());
        for (int i = 0; i < allElements.getLength(); i++) {
            Node current = allElements.item(i);
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
                resElements.add(currElemName);
            }
        }
        System.out.println(resElements);
        System.out.println(resElements.size());
    }
}