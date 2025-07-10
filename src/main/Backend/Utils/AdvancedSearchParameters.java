package main.Backend.Utils;

public class AdvancedSearchParameters {
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private BookStatus status;
    private Integer rating;

    public AdvancedSearchParameters() {}

    public AdvancedSearchParameters(String title, String author, String isbn, String genre, BookStatus status) {
        this.title = title; this.author = author; this.isbn = isbn; this.genre = genre; this.status = status;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

}
