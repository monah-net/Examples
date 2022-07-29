package XMLComparison;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;

import java.util.List;

class TestXml {

    public static void main(String[] args) throws Exception {
        String result = "<abc             attr=\"value1\"                title=\"something1\">            </abc>";
        // will be ok
        assertXMLEquals("<abc attr=\"value1\" title=\"something\"></abc>", result);
    }

    public static void assertXMLEquals(String expectedXML, String actualXML) throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));

        List<?> allDifferences = diff.getAllDifferences();
        Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());
    }

}