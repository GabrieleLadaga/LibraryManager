package test.Strategy.Sort;

import main.Backend.Builder.Book;
import main.Backend.Strategy.Sort.SortByAuthor;
import main.Backend.Strategy.Sort.SortStrategy;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SortByAuthorTest {
    private final SortStrategy sortStrategy = new SortByAuthor();

    @Test
    public void testSortByAuthor(){
        Book book1 = new Book.Builder("Dracula", "Stoker", "999").build();
        Book book2 = new Book.Builder("Ulisse", "Joyce", "333").build();
        Book book3 = new Book.Builder("Amleto", "Shakespeare", "753").build();

        List<Book> result = sortStrategy.sort(List.of(book1, book2, book3));

        assertEquals("Stoker", result.getLast().getAuthor());
        assertFalse(result.getFirst().getAuthor().equals("Shakespeare"));
    }

}
