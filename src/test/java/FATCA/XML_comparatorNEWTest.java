package FATCA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XML_comparatorNEWTest {

    @Test
    @DisplayName("CHECK TWO FILES WITH DIFFERENCIES")
    void xmlEquals() throws ParserConfigurationException, IOException, SAXException {
            assertEquals(false, XML_comparatorNEW.xmlEquals("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml", "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_noAccRef_linearized2.xml"));
            assertEquals(false, XML_comparatorNEW.xmlEquals("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_test_Lineriased.xml", "/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_noAccRef_linearized2.xml"));
    }
}