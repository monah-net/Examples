package FATCA;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class XsdElements {
    public static void main(String args[]) {
        Map <String,String> map = new HashMap<>();
        String fileName = "/Users/olegsolodovnikov/Desktop/ID_DOM/DJPDomestikBaruVERSI_1_0.xsd";
        String highLevelElem = "xs:element";
        String elemName = "xs:restriction base=\"xs:string\"";
        try {
            // parse the document
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(fileName));
            NodeList list = doc.getElementsByTagName(elemName);

            //loop to print data
            for(int i = 0 ; i < list.getLength(); i++)
            {
                Element first = (Element)list.item(i);
                if(first.hasAttributes())
                {
                    String nm = first.getAttribute("value");
                    //System.out.println(nm);
                    String scd = first.getTextContent().replaceAll("[\\n\\t]","");
                    //System.out.println(scd);
                    map.put(nm,scd);
                }
            }
        }
        catch (ParserConfigurationException e)

        {
            e.printStackTrace();
        } catch (IOException ed)
        {
            ed.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        try ( PrintWriter writer = new PrintWriter(new File(fileName + "_output.xsd")) ) {
            for ( Map.Entry<String, String> entry : map.entrySet() ) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
