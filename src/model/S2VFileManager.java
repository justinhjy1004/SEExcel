package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class S2VFileManager {

    /**
     * Reads a S2V formatted file and constructs a Spreadsheet.
     *
     * @param filePath The path to the S2V file.
     * @return A populated Spreadsheet instance.
     * @throws IOException If the file cannot be read.
     */
    public static Spreadsheet loadSpreadsheet(String filePath) throws IOException {
        Spreadsheet spreadsheet = new Spreadsheet();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 1; // Spreadsheet rows are 1-indexed

            while ((line = reader.readLine()) != null) {
                List<String> tokens = parseS2VLine(line);

                for (int col = 0; col < tokens.size(); col++) {
                    String token = tokens.get(col);

                    if (token == null || token.isEmpty()) {
                        continue; // Leave as default NoneContent
                    }

                    Coordinate coord = new Coordinate(getColumnName(col), row);
                    Cell cell = spreadsheet.getCell(coord);

                    if (token.startsWith("=")) {
                        // Formulas starting with '=' must have commas converted back to semicolons[cite: 10, 13]
                        String restoredFormula = token.replace(',', ';');
                        cell.setContent(new FormulaContent(restoredFormula));
                    } else if (isNumeric(token)) {
                        // Strictly numeric values become NumericalContent[cite: 10]
                        cell.setContent(new NumericalContent(Double.parseDouble(token)));
                    } else {
                        // Anything else is treated as TextContent[cite: 10]
                        cell.setContent(new TextContent(token));
                    }
                }
                row++;
            }
        }

        return spreadsheet;
    }

    /**
     * Parses a single line separated by semicolons, respecting values enclosed in double quotes.
     */
    private static List<String> parseS2VLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                inQuotes = !inQuotes; // Toggle quote state to ignore delimiters inside
            } else if (c == ';' && !inQuotes) {
                // Semicolon outside of quotes marks the end of a column token[cite: 12]
                tokens.add(currentToken.toString());
                currentToken.setLength(0);
            } else {
                currentToken.append(c);
            }
        }

        // Add the final token (or empty string if line ends with a semicolon)
        tokens.add(currentToken.toString());
        return tokens;
    }

    /**
     * Helper to check if a string strictly represents a valid number.
     */
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Helper to map 0-indexed integers to column letters (0 -> A, 26 -> AA)[cite: 9].
     * (Replicated from Spreadsheet.java to avoid modifying the existing class scope).
     */
    private static String getColumnName(int index) {
        StringBuilder columnName = new StringBuilder();
        int temp = index;
        while (temp >= 0) {
            columnName.insert(0, (char) ('A' + (temp % 26)));
            temp = (temp / 26) - 1;
        }
        return columnName.toString();
    }
}