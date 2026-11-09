package model;

public class NumericalContent implements Content {
    private final double value;

    public NumericalContent(double value) {
        this.value = value;
    }

    @Override
    public String getAsString() {
        return String.valueOf(value); // Returns the textual representation of the number[cite: 1].
    }

    @Override
    public double getAsNumber() {
        return value; // Returns the number itself[cite: 1].
    }
}
