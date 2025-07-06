package Backend.Strategy.Sort;

import Backend.Builder.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByGenre implements SortStrategy {

    @Override
    public List<Book> sort(List<Book> books) {
        List<Book> sortedBook = new ArrayList<>( books );
        sortedBook.sort( new GenreComparator() );
        return sortedBook;
    }

    static class GenreComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            return book1.getTitle().compareTo(book2.getTitle());
        }

    }
}
