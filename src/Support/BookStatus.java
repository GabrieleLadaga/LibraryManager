package Support;

public enum BookStatus {
    TO_READ("Da Leggere"),
    READING("In Lettura"),
    READ("Letto");

    private final String value;

    BookStatus(String value) { this.value = value; }

    public String getValue() { return value; }

}
