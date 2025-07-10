package test;

import main.Backend.Builder.Book;
import main.Backend.LibraryManager;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LibraryManagerTest {
    private LibraryManager libraryManager;

    @BeforeEach
    public void setUpAll() {
        libraryManager = new LibraryManager();
    }

    @Test
    public void testAddBook(){
        Book book = new Book.Builder("La Montagna Incantata", "Mann", "020").build();

        libraryManager.addBook(book);

        assertFalse(libraryManager.getBooks().isEmpty());
        assertTrue(libraryManager.getBooks().contains(book));
    }

    @Test
    public void testRemoveBook(){
        Book book = new Book.Builder("Sei Personaggi in Cerca d'Autore", "Pirandello", "021").build();
        libraryManager.addBook(book);

        libraryManager.removeBook(book);

        assertTrue(libraryManager.getBooks().isEmpty());
        assertFalse(libraryManager.getBooks().contains(book));
    }

    @Test
    public void testModifyBook(){
        Book book1 = new Book.Builder("La Fattoria degli Animali", "Arendt", "022").build();
        libraryManager.addBook(book1);

        Book book2 = new Book.Builder("Le Origini del Totalitarismo", "Arendt", "022").build();
        libraryManager.modifyBook(book1, book2);

        assertFalse(libraryManager.getBooks().contains(book1));
        assertTrue(libraryManager.getBooks().contains(book2));
    }

}
