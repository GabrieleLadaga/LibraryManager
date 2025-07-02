import Builder.Book;
import Observer.LibrarySubject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LibraryManager extends LibrarySubject {
    private List<Book> books;

    public LibraryManager() {
        books = new LinkedList<>();
    }

    public LibraryManager(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() { return Collections.unmodifiableList(books); }

    public void addBook(Book book) {
        books.add(book);
        notifyObservers();
    }

    public void removeBook(Book book) {
        books.remove(book);
        notifyObservers();
    }

    public void modifyBook(Book oldBook, Book newBook) {
        books.remove(oldBook);
        books.add(newBook);
        notifyObservers();
    }

}
