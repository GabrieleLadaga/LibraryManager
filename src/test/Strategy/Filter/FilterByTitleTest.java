package test.Strategy.Filter;

import main.Backend.Builder.Book;
import main.Backend.Strategy.Filter.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilterByTitleTest {
    private final FilterStrategy filter = new FilterByTitle();

    @Test
    public void testFilterWithMatchingTitle(){
        Book book1 = new Book.Builder("Il Piccolo Principle", "Exupéry", "123").build();
        Book book2 = new Book.Builder("La Divina Commedia", "Alighieri", "777").build();

        List<Book> result = filter.filter(List.of(book1, book2), "Il Piccolo Principe");

        assertEquals(1, result.size());
        assertEquals("Il Piccolo Principe", result.getFirst().getTitle());
    }

    @Test
    public void testFilterWithNoMatchingTitle(){
        Book book = new Book.Builder("I Malavoglia", "Verga", "456").build();

        List<Book> result = filter.filter(List.of(book), "Guerra e Pace");

        assertTrue(result.isEmpty());
    }

}
