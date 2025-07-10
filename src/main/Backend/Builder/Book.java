package main.Backend.Builder;

import main.Backend.Utils.BookStatus;

public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private final String genre;
    private final BookStatus status;
    private final Integer rating;

    private Book(Builder builder) {
        title = builder.title; author = builder.author; isbn = builder.isbn;
        genre = builder.genre; status = builder.status; rating = builder.rating;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public BookStatus getStatus() { return status; }
    public Integer getRating() { return rating; }

    public static class Builder {
        //Required Parameters
        private final String title;
        private final String author;
        private final String isbn;
        //Optional Parameters
        private String genre = null;
        private BookStatus status = BookStatus.DA_LEGGERE; //Valore di default
        private Integer rating = null;

        public Builder(String title, String author, String isbn) {
            this.title = title; this.author = author; this.isbn = isbn;
        }

        public Builder genre(String genre) {
            this.genre = genre; return this;
        }

        public Builder status(BookStatus status) {
            this.status = status != null ? status : BookStatus.DA_LEGGERE;
            return this;
        }

        public Builder rating(Integer rating) {
            if( rating != null && (rating < 1 || rating > 5) ) {
                System.err.println("Warning: Rating " + rating + " out of range!");
                this.rating = null;
            } else {
                this.rating = rating;
            }
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

}
