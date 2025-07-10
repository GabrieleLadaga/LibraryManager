package test.Strategy.Sort;

import main.Backend.Builder.Book;
import main.Backend.Strategy.Sort.SortByTitle;
import main.Backend.Strategy.Sort.SortStrategy;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SortByTitleTest {
    private final SortStrategy sortStrategy = new SortByTitle();

    @Test
    public void testSortByTitle(){
        Book book1 = new Book.Builder("Orgoglio e Pregiudizio", "Austin", "888").build();
        Book book2 = new Book.Builder("La Coscienza di Zeno", "Svevo", "486").build();
        Book book3 = new Book.Builder("Pinocchio", "Collodi", "486").build();

        List<Book> result = sortStrategy.sort(List.of(book1, book2, book3));

        assertEquals("La Coscienza di Zeno", result.getFirst().getTitle());
        assertTrue(result.size() == 3);
        assertFalse(result.getLast().getTitle().equals("Orgoglio e Pregiudizio"));
    }

}
