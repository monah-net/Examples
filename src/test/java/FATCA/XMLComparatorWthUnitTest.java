package FATCA;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLComparatorWthUnitTest {
    @Test
void compareXML() throws IOException, SAXException {
        FileInputStream fis1 = new FileInputStream("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_noAccRef.xml");
        FileInputStream fis2 = new FileInputStream("/Users/olegsolodovnikov/MyDocuments/FATCA/Comparator/xml_files/origin_fatca_det_uk_CP_noAccRef_TIN.xml");
        BufferedReader source = new BufferedReader(new InputStreamReader(fis1));
        BufferedReader target = new BufferedReader(new InputStreamReader(fis2));
        XMLUnit.setIgnoreWhitespace(true);
        assertEquals("===============================\n" +
                "Total differences : 2\n" +
                "================================\n" +
                "Expected text value '123456789' but was '1234567890' - comparing <TIN ...>123456789</TIN> at /AEOIUKSubmissionFIReport[1]/Submission[1]/FIReturn[1]/AccountData[1]/Organisation[1]/ControllingPerson[1]/Person[1]/HolderTaxInfo[1]/TIN[1]/text()[1] to <TIN ...>1234567890</TIN> at /AEOIUKSubmissionFIReport[1]/Submission[1]/FIReturn[1]/AccountData[1]/Organisation[1]/ControllingPerson[1]/Person[1]/HolderTaxInfo[1]/TIN[1]/text()[1]\n" +
                "Expected text value '1234567890' but was '123456789' - comparing <TIN ...>1234567890</TIN> at /AEOIUKSubmissionFIReport[1]/Submission[1]/FIReturn[1]/AccountData[1]/Organisation[1]/ControllingPerson[1]/Person[1]/HolderTaxInfo[1]/TIN[2]/text()[1] to <TIN ...>123456789</TIN> at /AEOIUKSubmissionFIReport[1]/Submission[1]/FIReturn[1]/AccountData[1]/Organisation[1]/ControllingPerson[1]/Person[1]/HolderTaxInfo[1]/TIN[2]/text()[1]",XMLComparatorWthUnit.compareXML(source,target));
    }


}