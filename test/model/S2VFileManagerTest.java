package model;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class S2VFileManagerTest {

    // Helper to get the absolute path of files in the test/resources folder
    private String getResourcePath(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        assertNotNull(url, "Test file not found: " + fileName);
        return new File(url.getFile()).getAbsolutePath();
    }

    @Test
    void testSpecExample() throws IOException {
        String filePath = getResourcePath("resources/testS2V1.txt");
        Spreadsheet sheet = S2VFileManager.loadSpreadsheet(filePath);

        // Row 1 checks[cite: 13]
        Cell a1 = sheet.getCell(new Coordinate("A", 1));
        assertInstanceOf(FormulaContent.class, a1.getContent());
        assertEquals("=C1+C2", a1.getContent().getAsString());

        Cell b1 = sheet.getCell(new Coordinate("B", 1));
        assertInstanceOf(NumericalContent.class, b1.getContent());
        assertEquals(4.0, b1.getContent().getAsNumber());

        Cell c1 = sheet.getCell(new Coordinate("C", 1));
        assertInstanceOf(NumericalContent.class, c1.getContent());
        assertEquals(1.0, c1.getContent().getAsNumber());

        // Row 2 checks (Empty cells and one number)[cite: 13]
        Cell a2 = sheet.getCell(new Coordinate("A", 2));
        assertInstanceOf(NoneContent.class, a2.getContent());

        Cell c2 = sheet.getCell(new Coordinate("C", 2));
        assertInstanceOf(NumericalContent.class, c2.getContent());
        assertEquals(2.0, c2.getContent().getAsNumber());

        // Row 3 checks (Text and Formula)[cite: 13]
        Cell b3 = sheet.getCell(new Coordinate("B", 3));
        assertInstanceOf(TextContent.class, b3.getContent());
        assertEquals("TOTAL", b3.getContent().getAsString());

        Cell c3 = sheet.getCell(new Coordinate("C", 3));
        assertInstanceOf(FormulaContent.class, c3.getContent());
        assertEquals("=A1+B1", c3.getContent().getAsString());
    }

    @Test
    void testQuotesRule() throws IOException {
        String filePath = getResourcePath("resources/testS2V2.txt");
        Spreadsheet sheet = S2VFileManager.loadSpreadsheet(filePath);

        // "hello;world" should remain together as text, stripped of quotes if your parser does so (currently your parser leaves quotes, which is fine for TextContent)
        Cell a1 = sheet.getCell(new Coordinate("A", 1));
        assertInstanceOf(TextContent.class, a1.getContent());
        assertEquals("hello;world", a1.getContent().getAsString());

        // "1,2" is not strictly numeric because of the comma, so it becomes TextContent
        Cell b1 = sheet.getCell(new Coordinate("B", 1));
        assertInstanceOf(TextContent.class, b1.getContent());
        assertEquals("1,2", b1.getContent().getAsString());

        // 3 is numeric
        Cell c1 = sheet.getCell(new Coordinate("C", 1));
        assertInstanceOf(NumericalContent.class, c1.getContent());
        assertEquals(3.0, c1.getContent().getAsNumber());
    }

    @Test
    void testFormulaCommaConversion() throws IOException {
        String filePath = getResourcePath("resources/testS2V3.txt");
        Spreadsheet sheet = S2VFileManager.loadSpreadsheet(filePath);

        // =SUMA(A1,B1) must become =SUMA(A1;B1)[cite: 13]
        Cell a1 = sheet.getCell(new Coordinate("A", 1));
        assertInstanceOf(FormulaContent.class, a1.getContent());
        assertEquals("=SUMA(A1;B1)", a1.getContent().getAsString());

        Cell b1 = sheet.getCell(new Coordinate("B", 1));
        assertInstanceOf(NumericalContent.class, b1.getContent());
        assertEquals(100.0, b1.getContent().getAsNumber());
    }
}