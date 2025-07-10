package test.Strategy.Search;

import main.Backend.Builder.Book;
import main.Backend.Strategy.Search.SearchByTitle;
import main.Backend.Strategy.Search.SearchStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchByTitleTest {
    private final SearchStrategy searchStrategy = new SearchByTitle();

    @Test
    public void testSearchWithMatchingTitle(){
        Book book1 = new Book.Builder("Frankenstein", "Shelly", "222").build();
        Book book2 = new Book.Builder("Moby Dick", "Melville", "111").build();

        List<Book> result = searchStrategy.search(List.of(book1, book2), "Frankenstein");

        assertEquals(1, result.size());
        assertEquals("Frankenstein", result.getFirst().getAuthor());
    }

    @Test
    public void testSearchWithNoMatchingTitle(){
        Book book = new Book.Builder("Il Codice Da Vinci", "Brown", "345").build();

        List<Book> result = searchStrategy.search(List.of(book), "I Promessi Sposi");

        assertTrue(result.isEmpty());
    }

}
