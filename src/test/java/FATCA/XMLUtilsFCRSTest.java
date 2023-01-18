package FATCA;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class XMLUtilsFCRSTest {
    @Test
    public void testCompareXML_IdenticalFiles() throws Exception {
        String file1 = new String("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case1_noDiff\\origin_GB_crs.xml");
        String file2 = new String("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case1_noDiff\\origin_GB_crsAccDataThesameDiffOrder.xml");
        XMLUtilsFCRS xmlComparison = new XMLUtilsFCRS();
        assertTrue(xmlComparison.compareXML(file1, file2));
    }

    @Test
    public void testCompareXML_DifferentFiles() throws Exception {
        String file1 = new String("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_addr_difference\\origin_GB_crs_Addr1.xml");
        String file2 = new String("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_addr_difference\\origin_GB_crs_Addr2.xml");
        XMLUtilsFCRS xmlComparison = new XMLUtilsFCRS();
        assertFalse(xmlComparison.compareXML(file1, file2));
    }

    @Test(expected = Exception.class)
    public void testCompareXMLFileNotFound() throws Exception {
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case2\\non-existent/file1.xml";
        String file2Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case2\\non-existent/file1.xml";
        XMLUtilsFCRS.compareXML(file1Path, file2Path);
    }
    @Test
    public void testCompareXML_DifferentFilescase4() throws Exception{
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4paymentDescDiff\\origin_GB_crs_Lineriased_ex1.xml";
        String file2Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4paymentDescDiff\\origin_GB_crs_Lineriased_ex2.xml";
        assertFalse(XMLUtilsFCRS.compareXML(file1Path,file2Path));
    }
    @Test
    public void testCompareXML_DifferentFilescase5() throws Exception{
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case5_diffMiddleName\\origin_GB_crs_Lineriased_ex1.xml";
        String file2Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case5_diffMiddleName\\origin_GB_crs_Lineriased_ex2.xml";
        assertFalse(XMLUtilsFCRS.compareXML(file1Path,file2Path));
    }
    @Test
    public void testCompareXML_DifferentFilescase6() throws Exception{
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case6DiffCPTIN\\origin_GB_crs_LineriasedVALID_CP.xml";
        String file2Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case6DiffCPTIN\\origin_GB_crs_LineriasedVALID_CP_TINdiff.xml";
        assertFalse(XMLUtilsFCRS.compareXML(file1Path,file2Path));
    }
    @Test
    public void testCompareXML_DifferentFilescase8() throws Exception{
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case8DiffTINCP\\origin_GB_crs_original.xml";
        String file2Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case8DiffTINCP\\origin_GB_crs_TIN_diff.xml";
        assertFalse(XMLUtilsFCRS.compareXML(file1Path,file2Path));
    }
    @Test
    public void testCompareXML_DifferentFilescase9() throws Exception{
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case9DiffTIN\\origin_GB_crs_original_case9.xml";
        String file2Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case9DiffTIN\\origin_GB_crs_original_case9_p2.xml";
        assertFalse(XMLUtilsFCRS.compareXML(file1Path,file2Path));
    }
    @Test (expected = Exception.class)
    public void testXMLFileSchemavalidation() throws Exception{
        String file1Path = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_acc_payment_difference\\origin_GB_crs_Lineriased.xml";
        String xmlSchemaPath = "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\xsdSchemas\\UK_Schema\\uk_aeoi_submission_v2.0.xsd";
        assertEquals("1",XMLUtilsFCRS.validateXMLWithSchema(file1Path,xmlSchemaPath));
    }
}