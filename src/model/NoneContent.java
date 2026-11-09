package model;

public class NoneContent implements Content {
    @Override
    public String getAsString() {
        return "";
    }

    @Override
    public double getAsNumber() {
        return 0.0; // An empty string evaluates to 0 when a number is requested[cite: 1].
    }
}