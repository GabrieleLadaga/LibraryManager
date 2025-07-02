package Strategy.Sort;

import Builder.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByRating implements SortStrategy {

    @Override
    public List<Book> sort(List<Book> books) {
        List<Book> sortedBook = new ArrayList<>( books );
        sortedBook.sort( new RatingComparator() );
        return sortedBook;
    }

    static class RatingComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            if( book1.getRating() == null && book2.getRating() == null ) return 0;
            if( book1.getRating() == null ) return 1;
            if( book2.getRating() == null ) return -1;
            return Integer.compare( book1.getRating(), book2.getRating() ); //Ordinamento Decrescente
        }

    }
}
