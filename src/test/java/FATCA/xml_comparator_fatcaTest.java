package FATCA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class xml_comparator_fatcaTest {
    @Test
    @DisplayName("Test case 1.Files identical")
    void xmlEquals_Test_case1() throws ParserConfigurationException, IOException, SAXException {
        assertFalse(xml_comparator_fatca.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case1_identical_files\\origin_fatca_det_uk_CP_linearized.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case1_identical_files\\origin_fatca_det_uk_CP_linearized_2.xml"));
    }

    @Test
    @DisplayName("Test case 2.Files not identical")
    void xmlEquals_Test_case2() throws ParserConfigurationException, IOException, SAXException {
        assertFalse(xml_comparator_fatca.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case2DiffInResCountryCodes\\origin_fatca_det_uk_CP_source.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case2DiffInResCountryCodes\\origin_fatca_det_uk_CP_target.xml"));
    }

    @Test
    @DisplayName("Test case 3.Files not identical")
    void xmlEquals_Test_case3() throws ParserConfigurationException, IOException, SAXException {
        assertFalse(xml_comparator_fatca.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_acc_payment_difference\\origin_GB_crs_Lineriased.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_acc_payment_difference\\origin_GB_crs_LineriasedUPD.xml"));
    }

    @Test
    @DisplayName("Test case 4.Files not identical")
    void xmlEquals_Test_case4() throws ParserConfigurationException, IOException, SAXException {
        assertFalse(xml_comparator_fatca.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4\\origin_GB_crs_Lineriased_ex1.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4\\origin_GB_crs_Lineriased_ex2.xml"));
    }

    @Test
    @DisplayName("Test case 5.Files not identical")
    void xmlEquals_Test_case5() throws ParserConfigurationException, IOException, SAXException {
        assertFalse(xml_comparator_fatca.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4\\origin_GB_crs_Lineriased_ex1.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4\\origin_GB_crs_Lineriased_ex2.xml"));
    }
}