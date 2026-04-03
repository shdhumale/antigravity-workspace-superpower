package com.ticketmgmt.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DescriptionEncryptionConverterTest {

    private DescriptionEncryptionConverter converter = new DescriptionEncryptionConverter();

    @Test
    public void testEncryptionDecryption() {
        String original = "This is a secret description";
        String encrypted = converter.convertToDatabaseColumn(original);
        assertNotEquals(original, encrypted);

        String decrypted = converter.convertToEntityAttribute(encrypted);
        assertEquals(original, decrypted);
    }

    @Test
    public void testHandlingUnencryptedData() {
        String unencrypted = "This was never encrypted";
        // Fixed behavior: return raw data instead of throwing exception
        String result = converter.convertToEntityAttribute(unencrypted);
        assertEquals(unencrypted, result);
    }
}
