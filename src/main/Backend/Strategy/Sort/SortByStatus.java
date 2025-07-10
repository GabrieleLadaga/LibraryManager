package main.Backend.Strategy.Sort;

import main.Backend.Builder.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByStatus implements SortStrategy {

    @Override
    public List<Book> sort(List<Book> books) {
        List<Book> sortedBook = new ArrayList<>( books );
        sortedBook.sort( new StatusComparator() );
        return sortedBook;
    }

    static class StatusComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            return book1.getStatus().compareTo(book2.getStatus());
        }

    }

}
