package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XMLComparator {
    public static void main(String[] args) {
        try {
            Map<String , String> resultMap = new HashMap<>();
            File file = new File("/Users/olegsolodovnikov/Desktop/test/submission/id021/CRS_20200422002628_CRS7012019_B_11111111111_12345678901_T.xml");
            File file2 = new File("/Users/olegsolodovnikov/Desktop/test/etalon/id021/LU_C_AGG_EmptyDocSpec_UNIT_TESTs.xml");
            String elementname = "crs:CrsBody";
            System.out.println(file.exists());
            System.out.println(file2.exists());
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            DocumentBuilder dBuilder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Document doc2 = dBuilder2.parse(file2);
            NodeList nodelist = doc.getElementsByTagName(elementname);
            NodeList nodeList2 = doc2.getElementsByTagName(elementname);
            System.out.println(nodelist.getLength() + ":" + nodeList2.getLength());
            for (int i = 0; i < nodelist.getLength(); i++) {
                resultMap.put(elementname + i,"");
            }
            for (int counter = 0; counter < nodelist.getLength(); counter++) {
                if(nodelist.item(counter).getNodeType() == Node.ELEMENT_NODE) {
                    for (int counter2 = 0; counter2 < nodeList2.getLength(); counter2++) {
                        if (nodeList2.item(counter2).getNodeType() == Node.ELEMENT_NODE) {
                            if (nodelist.item(counter).getTextContent().trim().equals(nodeList2.item(counter2).getTextContent().trim())) {
                                resultMap.replace(elementname + counter,"DONE");
                                break;
                            }
                        }
                    }
                }
            }
            for (Map.Entry <String, String> pair : resultMap.entrySet()
            ) {
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}