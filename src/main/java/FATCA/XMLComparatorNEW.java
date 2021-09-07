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
        final int input = 0;
        final int output = 1;
        String[] files = new String[2];
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("FIReturn", "FIReturnRef");
        mapa.put("AccountData", "AccountRef");
        mapa.put("PoolReport", "PoolReportRef");
        ArrayList resElementsIn = new ArrayList();
        ArrayList resElementsOut = new ArrayList();
        files[input] = "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_GB_crs_Lineriased.xml";
        files[output] = "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_GB_crsUPD_oneLine.xml";
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        List[] resElements = new List[]{new ArrayList(), new ArrayList()};
//        get all elements from file into resElementsIn array
        for (int fileCounter = 0; fileCounter < 2; fileCounter++) {
            File fileInput = new File(files[fileCounter]);
            Document javaParsedInput = dBuilderInput.parse(fileInput);
            NodeList allElementsIn = javaParsedInput.getElementsByTagName("*");
            for (int i = 0; i < allElementsIn.getLength(); i++) {
                Node current = allElementsIn.item(i);
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
//                  Get Reference value into the parsed string
                        String reference = "";
                        if (mapa.containsKey(current.getNodeName()) && current.getFirstChild().getFirstChild().getNodeName().equals(mapa.get(current.getNodeName()))) {
                            reference = current.getFirstChild().getFirstChild().getTextContent();
                            System.out.println("reference : " + current.getFirstChild().getFirstChild().getNodeName() + reference);
                        }
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
                        if (reference.equals("")) {
                            currElemName = current.getNodeName() + attributeParent + "." + currElemName;
                        } else {
                            currElemName = current.getNodeName() + ":" + reference + attributeParent + "." + currElemName;
                        }
                    }
                    resElements[fileCounter].add(currElemName);
                }
            }
        }

//        Compairing arrays

        List resElementsInTemp = new ArrayList();
        List resElementsOutTemp = new ArrayList();
        Set<String> dublicates = new HashSet<String>(resElements[output]);
        System.out.println(dublicates.size());
        System.out.println(resElements[output].size());
        System.out.println(resElements[output]);
        System.out.println(dublicates);
        resElementsInTemp.addAll(resElements[input]);
        resElementsOutTemp.addAll(resElements[output]);
//        System.out.println(resElementsInTemp);
//        System.out.println(resElementsOutTemp);
//        resElements[output].removeAll(resElementsInTemp);
//        System.out.println(resElements[input]);
//        System.out.println(resElements[output]);
        System.out.println(resElements[input]);
        System.out.println(resElements[input].isEmpty());
        System.out.println(resElements[input].size());
//        System.out.println( resElements[output].isEmpty());
    }
}