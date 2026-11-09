package model;

// FormulaContent.java
public class FormulaContent implements Content {
    private final String formula;

    public FormulaContent(String input) {
        // A formula is a mathematical relationship that always starts with the character '='[cite: 1].
        this.formula = input;
    }

    @Override
    public String getAsString() {
        return formula;
    }

    @Override
    public double getAsNumber() {
        // TODO: Parse the formula string into operands/functions, compute the mathematical relationship, and return the resulting number[cite: 1, 2].
        return 0.0;
    }
}