package model;

import java.util.HashMap;
import java.util.Map;

public class Spreadsheet {
    private final Map<Coordinate, Cell> cells;

    public Spreadsheet() {
        this.cells = new HashMap<>();
        initializeGrid();
    }

    private void initializeGrid() {
        for (char col = 'A'; col <= 'Z'; col++) {
            String columnStr = String.valueOf(col);
            for (int row = 1; row <= 26; row++) {
                Coordinate coord = new Coordinate(columnStr, row);
                cells.put(coord, new Cell(coord));
            }
        }
    }

    public Cell getCell(Coordinate coordinate) {
        // If a cell is requested outside the initial A1:Z26 grid, create it dynamically
        return cells.computeIfAbsent(coordinate, Cell::new);
    }

    public void printAsString() {
        printAsString(10, 10);
    }

    public void printAsString(int numCols, int numRows) {
        System.out.println("\n--- Spreadsheet View ---");

        // Print column headers
        System.out.print(String.format("%-8s", ""));
        for (int c = 0; c < numCols; c++) {
            System.out.print(String.format("| %-12s", getColumnName(c)));
        }
        System.out.println("|");

        // Print rows and cell contents
        for (int r = 1; r <= numRows; r++) {
            System.out.print(String.format("%-8d", r));
            for (int c = 0; c < numCols; c++) {
                Coordinate coord = new Coordinate(getColumnName(c), r);
                Cell cell = getCell(coord);
                String displayValue = cell.getContent().getAsString();

                // Truncate long strings for cleaner console output
                if (displayValue.length() > 12) {
                    displayValue = displayValue.substring(0, 9) + "...";
                }
                System.out.print(String.format("| %-12s", displayValue));
            }
            System.out.println("|");
        }
        System.out.println("------------------------\n");
    }

    // Helper to generate sequences of letters for columns (A, B, ..., Z, AA, AB)[cite: 1]
    private String getColumnName(int index) {
        StringBuilder columnName = new StringBuilder();
        int temp = index;
        while (temp >= 0) {
            columnName.insert(0, (char) ('A' + (temp % 26)));
            temp = (temp / 26) - 1;
        }
        return columnName.toString();
    }
}