package FATCA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XML_comparatorNEW_UKTest {
    @Test
    @DisplayName("CHECK TWO FILES WITH DIFFERENCES")
    void xmlEquals_Test_case1() {
        assertAll(() -> assertTrue(XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case1_identical_files\\origin_fatca_det_uk_CP_linearized.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case1_identical_files\\origin_fatca_det_uk_CP_linearized_2.xml")),
                () -> assertFalse(XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case2DiffInResCountryCodes\\origin_fatca_det_uk_CP_source.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case2DiffInResCountryCodes\\origin_fatca_det_uk_CP_target.xml")),
                () -> assertFalse(XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_acc_payment_difference\\origin_GB_crs_Lineriased.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case3_acc_payment_difference\\origin_GB_crs_LineriasedUPD.xml")),
                () -> assertFalse(XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4\\origin_GB_crs_Lineriased_ex1.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case4\\origin_GB_crs_Lineriased_ex2.xml")),
                () -> assertFalse(XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case5\\origin_GB_crs_Lineriased_ex1.xml", "C:\\Users\\osolodovnikov\\IdeaProjects\\Examples\\src\\test\\files\\comparator\\case5\\origin_GB_crs_Lineriased_ex2.xml")));
    }
}