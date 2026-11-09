// Main.java
import model.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Spreadsheet spreadsheet = new Spreadsheet();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Spreadsheet Initialized.");

        // Presenting a textual menu of options and interacting via a sequence of questions/answers[cite: 5].
        while (running) {
            System.out.println("Options:");
            System.out.println("1. Edit a cell");
            System.out.println("2. Print spreadsheet (default 10x10)");
            System.out.println("3. Print spreadsheet (custom dimensions)");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter cell coordinate (e.g., A1): ");
                    String coordInput = scanner.nextLine().trim().toUpperCase();

                    try {
                        // Extract letters for column and numbers for row[cite: 1]
                        String col = coordInput.replaceAll("[^A-Z]", "");
                        int row = Integer.parseInt(coordInput.replaceAll("[^0-9]", ""));
                        Coordinate coord = new Coordinate(col, row);

                        System.out.print("Enter content (start with '=' for formula, else text/number): ");
                        String contentInput = scanner.nextLine().trim();

                        Cell cell = spreadsheet.getCell(coord);

                        // Assign proper content type based on input rules
                        if (contentInput.isEmpty()) {
                            cell.setContent(new NoneContent());
                        } else if (contentInput.startsWith("=")) {
                            cell.setContent(new FormulaContent(contentInput));
                        } else {
                            try {
                                double num = Double.parseDouble(contentInput);
                                cell.setContent(new NumericalContent(num));
                            } catch (NumberFormatException e) {
                                cell.setContent(new TextContent(contentInput));
                            }
                        }
                        System.out.println("Cell " + coordInput + " updated successfully.\n");
                    } catch (Exception e) {
                        // Inform the user through a message in the console if there is a problem[cite: 5]
                        System.out.println("Error: Invalid coordinate format. Please use letters followed by numbers (e.g., B2).\n");
                    }
                    break;

                case "2":
                    spreadsheet.printAsString();
                    break;

                case "3":
                    try {
                        System.out.print("Enter number of columns to display: ");
                        int cols = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter number of rows to display: ");
                        int rows = Integer.parseInt(scanner.nextLine().trim());
                        spreadsheet.printAsString(cols, rows);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter valid integers for dimensions.\n");
                    }
                    break;

                case "4":
                    running = false;
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, 3, or 4.\n");
            }
        }
        scanner.close();
    }
}