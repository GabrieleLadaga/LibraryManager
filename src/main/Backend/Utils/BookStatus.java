package main.Backend.Utils;

public enum BookStatus {
    DA_LEGGERE("Da Leggere"),
    IN_LETTURA("In Lettura"),
    LETTO("Letto");

    private final String value;

    BookStatus(String value) { this.value = value; }

    public String getValue() { return value; }

}
