package org.openeid.cdoc4j;

import org.junit.Test;
import org.openeid.cdoc4j.token.pkcs12.PKCS12Token;
import org.openeid.cdoc4j.xml.exception.XmlParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CDOCParserTest {

    @Test
    public void getDataFileNamesSuccessful_cdoc11_withOneDataFile() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc11_ECC.cdoc");
        List<String> dataFileNames = CDOCParser.getDataFileNames(cdocStream);
        assertTrue(dataFileNames.size() == 1);
        assertEquals("lorem1.txt", dataFileNames.get(0));
    }

    @Test
    public void getDataFileNamesSuccessful_cdoc11_withTwoDataFiles() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc11_ECC_withDDOC.cdoc");
        List<String> dataFileNames = CDOCParser.getDataFileNames(cdocStream);
        assertTrue(dataFileNames.size() == 2);
        assertEquals("lorem1.txt", dataFileNames.get(0));
        assertEquals("lorem2.txt", dataFileNames.get(1));
    }

    @Test
    public void getDataFileNamesSuccessful_cdoc10_withOneDataFile() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc10.cdoc");
        List<String> dataFileNames = CDOCParser.getDataFileNames(cdocStream);
        assertTrue(dataFileNames.size() == 1);
        assertEquals("test.txt", dataFileNames.get(0));
    }

    @Test
    public void getDataFileNamesSuccessful_cdoc10_withTwoDataFiles() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc10_withDDOC.cdoc");
        List<String> dataFileNames = CDOCParser.getDataFileNames(cdocStream);
        assertTrue(dataFileNames.size() == 2);
        assertEquals("lorem1.txt", dataFileNames.get(0));
        assertEquals("lorem2.txt", dataFileNames.get(1));
    }

    @Test(expected = XmlParseException.class)
    public void getDataFileNames_withInvalidCDOCStructure_shouldThrowException() throws FileNotFoundException, XmlParseException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/invalid_cdoc11_structure.cdoc");
        CDOCParser.getDataFileNames(cdocStream);
    }

    @Test(expected = XmlParseException.class)
    public void getDataFileNames_withEntityExpansionAttack_shouldThrowException() throws Exception {
        InputStream cdocStream = getClass().getResourceAsStream("/cdoc/1.0-XXE.cdoc");
        CDOCParser.getDataFileNames(cdocStream);

    }

    @Test
    public void getRecipients_cdoc10_withOneRecipient() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc10.cdoc");
        List<Recipient> recipients = CDOCParser.getRecipients(cdocStream);
        assertTrue(recipients.size() == 1);
        assertEquals("ŽÕRINÜWŠKY,MÄRÜ-LÖÖZ,11404176865", recipients.get(0).getCN());
    }

    @Test
    public void getRecipients_cdoc10_withMultipleRecipients() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc10_withMultipleRecipients.cdoc");
        List<Recipient> recipients = CDOCParser.getRecipients(cdocStream);
        assertTrue(recipients.size() == 2);
        assertEquals("ŽÕRINÜWŠKY,MÄRÜ-LÖÖZ,11404176865", recipients.get(0).getCN());
        assertEquals("ŽÕRINÜWŠKY,MÄRÜ-LÖÖZ,11404176865", recipients.get(1).getCN());
    }

    @Test
    public void getRecipients_cdoc11_withOneRecipient() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc11_ECC.cdoc");
        List<Recipient> recipients = CDOCParser.getRecipients(cdocStream);
        assertTrue(recipients.size() == 1);
        assertEquals("TESTNUMBER,ECC,14212128029", recipients.get(0).getCN());
    }

    @Test
    public void getRecipients_cdoc11_withMultipleRecipients() throws XmlParseException, FileNotFoundException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/valid_cdoc11_withMultipleRecipients.cdoc");
        List<Recipient> recipients = CDOCParser.getRecipients(cdocStream);
        assertTrue(recipients.size() == 2);
        assertEquals("TESTNUMBER,ECC,14212128029", recipients.get(0).getCN());
        assertEquals("ŽÕRINÜWŠKY,MÄRÜ-LÖÖZ,11404176865", recipients.get(1).getCN());
    }

    @Test(expected = XmlParseException.class)
    public void getRecipients__withInvalidCDOCStructure_shouldThrowException() throws FileNotFoundException, XmlParseException {
        FileInputStream cdocStream = new FileInputStream("src/test/resources/cdoc/invalid_cdoc11_structure.cdoc");
        CDOCParser.getRecipients(cdocStream);
    }

    @Test(expected = XmlParseException.class)
    public void getRecipients_withEntityExpansionAttack_shouldThrowException() throws Exception {
        InputStream cdocStream = getClass().getResourceAsStream("/cdoc/1.0-XXE.cdoc");
        CDOCParser.getDataFileNames(cdocStream);
    }
}
