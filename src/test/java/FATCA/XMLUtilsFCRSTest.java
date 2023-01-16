package FATCA;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XMLUtilsFCRSTest {
    @Test
    public void testCompareXML_IdenticalFiles() throws Exception {
        String file1 = new String("/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_Lineriased.xml");
        String file2 = new String("/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_Lineriased.xml");
        XMLUtilsFCRS xmlComparison = new XMLUtilsFCRS();
        assertTrue(xmlComparison.compareXML(file1, file2));
    }

    @Test
    public void testCompareXML_DifferentFiles() throws Exception {
        String file1 = new String("/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_Lineriased.xml");
        String file2 = new String("/Users/olegsolodovnikov/IdeaProjects/Examples/src/test/files/comparator/case3_acc_payment_difference/origin_GB_crs_LineriasedUPD.xml");
        XMLUtilsFCRS xmlComparison = new XMLUtilsFCRS();
        assertFalse(xmlComparison.compareXML(file1, file2));
    }

    @Test(expected = Exception.class)
    public void testCompareXMLFileNotFound() throws Exception {
        String file1Path = "/path/to/non-existent/file1.xml";
        String file2Path = "/path/to/non-existent/file2.xml";
        XMLUtilsFCRS.compareXML(file1Path, file2Path);
    }
}