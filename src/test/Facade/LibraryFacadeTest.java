package test.Facade;

import main.Backend.Builder.Book;
import main.Backend.Facade.LibraryFacade;
import main.Backend.Strategy.Filter.FilterByAuthor;
import main.Backend.Strategy.Sort.SortByAuthor;
import main.Backend.Utils.AdvancedSearchParameters;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.*;

public class LibraryFacadeTest {
    private LibraryFacade libraryFacade;

    @BeforeEach
    public void setUpAll() {
        libraryFacade = new LibraryFacade();
    }

    @Test
    public void testAddBook() {
        Book book = new Book.Builder("Il Signore degli Anelli", "Tolkien", "004").build();
        libraryFacade.addBook(book);

        assertTrue(libraryFacade.getLibraryManager().getBooks().size() == 1);
    }

    @Test
    public void testRemoveBook() {
        Book book = new Book.Builder("I Tre Moschettieri", "Dumas", "005").build();
        libraryFacade.getLibraryManager().addBook(book);

        libraryFacade.removeBook(book);

        assertTrue(libraryFacade.getLibraryManager().getBooks().isEmpty());
    }

    @Test
    public void testModifyBook() {
        Book book = new Book.Builder("L'Isola del Tesoro", "Calvino", "006").build();
        libraryFacade.getLibraryManager().addBook(book);

        Book correctbook = new Book.Builder("Il Barone Rampante", "Calvino", "006").build();

        libraryFacade.modifyBook(book, correctbook);

        assertFalse(libraryFacade.getLibraryManager().getBooks().getFirst().getTitle().equals("L'Isola del Tesoro"));
        assertTrue(libraryFacade.getLibraryManager().getBooks().getFirst().getTitle().equals("Il Barone Rampante"));
    }

    @Test
    public void testSearchByTitle() {
        Book book1 = new Book.Builder("Dialogo sopra i due massimi sistemi del mondo", "Galilei", "007").build();
        Book book2 = new Book.Builder("Storie", "Erodoto", "008").build();

        libraryFacade.getLibraryManager().addBook(book1);
        libraryFacade.getLibraryManager().addBook(book2);

        List<Book> result = libraryFacade.searchByTitle("Storie");

        assertFalse(result.size() == 2);
        assertTrue(result.getFirst().getTitle().equals("Storie"));
    }

    @Test
    public void testAdvancedSearch() {
        Book book1 = new Book.Builder("Via col Vento", "Mitchell", "009").build();
        Book book2 = new Book.Builder("Harry Potter", "Rowling", "010").build();

        libraryFacade.getLibraryManager().addBook(book1);
        libraryFacade.getLibraryManager().addBook(book2);

        List<Book> result = libraryFacade.advancedSearch(new AdvancedSearchParameters(null, "Mitchell", null, null, null));

        assertFalse(result.size() == 2);
        assertTrue(result.getFirst().getAuthor().equals("Mitchell"));
    }

    @Test
    public void testGetSortedBook() {
        Book book1 = new Book.Builder("Alice nel Paese delle Meraviglie", "Carroll", "011").build();
        Book book2 = new Book.Builder("Critica della Ragion Pratica", "Kant", "012").build();
        Book book3 = new Book.Builder("L'Eneide", "Virgilio", "013").build();

        libraryFacade.getLibraryManager().addBook(book1);
        libraryFacade.getLibraryManager().addBook(book2);
        libraryFacade.getLibraryManager().addBook(book3);

        libraryFacade.setSortStrategy(new SortByAuthor());
        List<Book> result = libraryFacade.getSortedBook();

        assertFalse(result.size() == 1);
        assertTrue(result.getFirst().getAuthor().equals("Carroll"));
    }

    @Test
    public void testGetFilterBook() {
        Book book1 = new Book.Builder("Così parlò zaratustra", "Nietzsche", "014").build();
        Book book2 = new Book.Builder("Iliade", "Omero", "015").build();
        Book book3 = new Book.Builder("Odissea", "Omero", "016").build();

        libraryFacade.getLibraryManager().addBook(book1);
        libraryFacade.getLibraryManager().addBook(book2);
        libraryFacade.getLibraryManager().addBook(book3);

        libraryFacade.setFilterStrategy(new FilterByAuthor());
        List<Book> result = libraryFacade.getFilterBook("Omero");

        assertFalse(result.isEmpty());
        assertTrue(result.getFirst().getAuthor().equals("Omero") && result.getLast().getAuthor().equals("Omero"));
    }

}
