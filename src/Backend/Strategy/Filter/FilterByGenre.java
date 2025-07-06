package Backend.Strategy.Filter;

import Backend.Builder.Book;

import java.util.LinkedList;
import java.util.List;

public class FilterByGenre implements FilterStrategy {

    @Override
    public List<Book> filter(List<Book> books, String value) {
        List<Book> filteredBooks = new LinkedList<>();
        for ( Book book : books ) {
            if ( book.getGenre().equalsIgnoreCase(value) ) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

}
