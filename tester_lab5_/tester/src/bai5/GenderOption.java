package bai5;

public enum GenderOption {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    private final String label;

    GenderOption(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
