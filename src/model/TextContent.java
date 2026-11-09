package model;

// 4. TextContent
public class TextContent implements Content {
    private final String text;

    public TextContent(String text) {
        this.text = text != null ? text : "";
    }

    @Override
    public String getAsString() {
        return text; // Returns the string itself[cite: 1].
    }

    @Override
    public double getAsNumber() {
        if (text.isEmpty()) {
            return 0.0; // Empty string results in 0[cite: 1].
        }
        try {
            return Double.parseDouble(text); // Textual representation of a number results in the number[cite: 1].
        } catch (NumberFormatException e) {
            // Must notify an error if the string is not a textual representation of a number[cite: 1].
            throw new IllegalArgumentException("Error: Text is not a valid number.");
        }
    }
}