package Backend;

import Backend.Builder.Book;
import Backend.Observer.LibrarySubject;
import Backend.Singleton.DatabaseManager;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LibraryManager extends LibrarySubject {
    private List<Book> books;
    private DatabaseManager dbManager;

    public LibraryManager() {
        dbManager = DatabaseManager.getInstance();
        books = loadBooksFromDatabase();
    }

    public LibraryManager(List<Book> books) {
        this.books = books;
    }

    private List<Book> loadBooksFromDatabase() {
        try {
            return dbManager.getAllBooks();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return new LinkedList<>();
        }
    }

    public List<Book> getBooks() { return Collections.unmodifiableList(books); }

    public void addBook(Book book) {
        try {
            dbManager.addBook(book);
            books.add(book);
            notifyObservers();
        }catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removeBook(Book book) {
        try {
            dbManager.deleteBook(book);
            books.remove(book);
            notifyObservers();
        }catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void modifyBook(Book oldBook, Book newBook) {
        try {
            if(oldBook.getIsbn().equals(newBook.getIsbn())) {
                dbManager.updateBook(newBook);
            } else {
                dbManager.deleteBook(oldBook);
                dbManager.addBook(newBook);
            }
            books.remove(oldBook);
            books.add(newBook);
            notifyObservers();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
