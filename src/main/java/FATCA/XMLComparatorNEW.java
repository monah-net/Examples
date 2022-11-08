package FATCA;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

class XML_comparatorNEW {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("TEST");
    }

    static boolean xmlEquals(String file1,String file2) throws ParserConfigurationException, IOException, SAXException {
        boolean compareResult = true;
        final String MULTI = "MULTI";
        final String SINGLE = "SINGLE";
        final String ALL_SIBLINGS = "ALL_SIBLINGS";
        final int input = 0;
        final int output = 1;
        String[] files = new String[2];
        Map<String, String[]> params = new HashMap<>();
        params.put("FIReturn", new String[]{"FIReturnRef", "2",SINGLE});
        params.put("AccountData", new String[]{"AccountRef", "2",SINGLE});
        params.put("PoolReport", new String[]{"PoolReportRef", "2",SINGLE});
//        params.put("HolderTaxInfo", new String[]{"TIN", "1",MULTI});
        params.put("HolderTaxInfo", new String[]{"TIN", "1",ALL_SIBLINGS});
        files[input] = file1;
        files[output] = file2;
        DocumentBuilder dBuilderInput = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        List[] resElements = new List[]{new ArrayList(), new ArrayList()};
//        get all elements from file into resElementsIn array
        for (int fileCounter = 0; fileCounter < files.length; fileCounter++) {
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
                        if (params.containsKey(current.getNodeName())) {
//                    get subNode
                            Integer elementLevel = Integer.valueOf(params.get(current.getNodeName())[1]);
                            Node subElement = current;
//                            System.out.println("subElement " + subElement);
                            for (int j = 0; j < elementLevel; j++) {
                                subElement = subElement.getFirstChild();
                            }
                            if (subElement.getNodeName() == params.get(current.getNodeName())[0]) {
//                                System.out.println(subElement.getNodeName());
                                if (params.get(current.getNodeName())[2] == SINGLE){
                                    reference = subElement.getTextContent();}
                                else if (params.get(current.getNodeName())[2] == MULTI){
                                    StringBuilder multiReferenceStr = new StringBuilder();
                                    List multiReference = new ArrayList<>();
                                    String test = new String();
                                    while(subElement.getNodeName() == params.get(current.getNodeName())[0]){
                                        multiReference.add(subElement.getTextContent());
                                        subElement = subElement.getNextSibling();
                                        test = subElement.getTextContent();
                                    }
                                    Collections.sort(multiReference);
                                    for (int j = 0; j < multiReference.size(); j++) {
                                        multiReferenceStr.append(multiReference.get(j));
                                    }
                                    reference = multiReferenceStr.toString();
                                }
                                System.out.println("reference" + reference);
                            } else {
                                while (subElement.getNodeName() != params.get(current.getNodeName())[0] && !(subElement instanceof Element)) {
                                    subElement = subElement.getNextSibling();
                                }
                                reference = "N/A";
                            }
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

//        Comparing arrays
        List resElementsInTemp = new ArrayList();
        List resElementsOutTemp = new ArrayList();
        resElementsInTemp.addAll(resElements[input]);
        resElementsOutTemp.addAll(resElements[output]);
        resElements[output].removeAll(resElementsInTemp);
        System.out.println(resElements[input]);
        System.out.println(resElements[input].isEmpty());
        System.out.println(resElements[input].size());
        System.out.println(resElements[output].isEmpty());
        System.out.println(resElements[output].size());
        System.out.println(resElements[output]);
        if (!(resElements[input].isEmpty() && resElements[output].isEmpty())){
            compareResult = false;
        }
        return compareResult;
    }
}