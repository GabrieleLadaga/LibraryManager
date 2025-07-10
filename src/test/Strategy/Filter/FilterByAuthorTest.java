package test.Strategy.Filter;

import main.Backend.Builder.Book;
import main.Backend.Strategy.Filter.FilterByAuthor;
import main.Backend.Strategy.Filter.FilterStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilterByAuthorTest {
    private final FilterStrategy filter = new FilterByAuthor();

    @Test
    public void testFilterWithMatchingAuthor(){
        Book book1 = new Book.Builder("Il Principe", "Machiavelli", "469").build();
        Book book2 = new Book.Builder("Il Giovane Holden", "Salinger", "444").build();

        List<Book> result = filter.filter(List.of(book1, book2), "Machiavelli");

        assertEquals(1, result.size());
        assertEquals("Machiavelli", result.getFirst().getAuthor());
    }

    @Test
    public void testFilterWithNoMatchingAuthor(){
        Book book = new Book.Builder("Il Vecchio e il Mare", "Hemingway", "951").build();

        List<Book> result = filter.filter(List.of(book), "Darwin");

        assertTrue(result.isEmpty());
    }

}
