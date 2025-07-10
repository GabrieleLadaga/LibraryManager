package test.Strategy.Search;

import main.Backend.Builder.Book;
import main.Backend.Strategy.Search.AdvancedSearch;
import main.Backend.Strategy.Search.AdvancedSearchStrategy;
import main.Backend.Utils.AdvancedSearchParameters;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AdvancedSearchTest {
    private final AdvancedSearchStrategy advancedSearchStrategy = new AdvancedSearch();

    @Test
    public void testAdvancedSearchWithMatch(){
        Book book1 = new Book.Builder("Il Fu Mattia Pascal", "Pirandello", "001").build();
        Book book2 = new Book.Builder("Cappuccetto Rosso", "Grimm", "002").build();

        List<Book> result = advancedSearchStrategy.search(List.of(book1, book2), new AdvancedSearchParameters(null, "Pirandello", null, null, null));

        assertEquals(1, result.size());
        assertEquals("Pirandello", result.getFirst().getAuthor());
    }

    @Test
    public void testAdvancedSearchWithNoMatch(){
        Book book = new Book.Builder("La Metamorfosi", "Kafka", "003").build();

        List<Book> result = advancedSearchStrategy.search(List.of(book), new AdvancedSearchParameters("Il Gattopardo", null,null, null, null));

        assertTrue(result.isEmpty());
    }
}
