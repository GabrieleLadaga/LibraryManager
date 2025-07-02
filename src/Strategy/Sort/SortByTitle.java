package Strategy.Sort;

import Builder.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByTitle implements SortStrategy {

    @Override
    public List<Book> sort(List<Book> books) {
        List<Book> sortedBook = new ArrayList<>( books );
        sortedBook.sort( new TitleComparator() );
        return sortedBook;
    }

    static class TitleComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            return book1.getTitle().compareTo(book2.getTitle());
        }

    }

}
