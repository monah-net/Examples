package FATCA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XML_comparatorNEW_UKTest {
    @Test
    @DisplayName("CHECK TWO FILES WITH DIFFERENCIES")
    void xmlEquals_Test_case1() throws ParserConfigurationException, IOException, SAXException {
        assertAll(() -> assertEquals(false, XML_comparatorNEW_UK.xmlEquals("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml", "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_noAccRef_linearized2.xml")),
                () -> assertEquals(true, XML_comparatorNEW_UK.xmlEquals("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml", "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml")),
                () -> assertEquals(true, XML_comparatorNEW_UK.xmlEquals("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/case1_identical_files/origin_fatca_det_uk_CP_linearized.xml", "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/case1_identical_files/origin_fatca_det_uk_CP_linearized_2.xml")),
                () -> assertEquals(false, XML_comparatorNEW_UK.xmlEquals("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/case2DiffInResCountryCodes/origin_fatca_det_uk_CP_source.xml", "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/case2DiffInResCountryCodes/origin_fatca_det_uk_CP_target.xml")));
    }
}