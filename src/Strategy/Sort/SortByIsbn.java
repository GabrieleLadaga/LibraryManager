package Strategy.Sort;

import Builder.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByIsbn implements SortStrategy {

    @Override
    public List<Book> sort(List<Book> books) {
        List<Book> sortedBook = new ArrayList<>( books );
        sortedBook.sort( new IsbnComparator() );
        return sortedBook;
    }

    static class IsbnComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            return book1.getIsbn().compareTo(book2.getIsbn());
        }

    }
}
