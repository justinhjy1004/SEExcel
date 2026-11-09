package model;

public class Cell {
    private final Coordinate coordinate;
    private Content content;

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.content = new NoneContent(); // Cells initialize as empty by default.
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}