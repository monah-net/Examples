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
        assertAll(() -> assertEquals(true, XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\workingdir\\Comparator\\case1_identical_files\\origin_fatca_det_uk_CP_linearized.xml", "C:\\Users\\osolodovnikov\\workingdir\\Comparator\\case1_identical_files\\origin_fatca_det_uk_CP_linearized_2.xml")),
                () -> assertEquals(false, XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\workingdir\\Comparator\\case2DiffInResCountryCodes\\origin_fatca_det_uk_CP_source.xml", "C:\\Users\\osolodovnikov\\workingdir\\Comparator\\case2DiffInResCountryCodes\\origin_fatca_det_uk_CP_target.xml")),
                () -> assertEquals(false, XML_comparatorNEW_UK.xmlEquals("C:\\Users\\osolodovnikov\\workingdir\\Comparator\\case3_acc_payment_difference\\origin_GB_crs_Lineriased.xml", "C:\\Users\\osolodovnikov\\workingdir\\Comparator\\case3_acc_payment_difference\\origin_GB_crs_LineriasedUPD.xml")));
    }
}