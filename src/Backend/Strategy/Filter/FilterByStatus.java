package Backend.Strategy.Filter;

import Backend.Builder.Book;
import Backend.Utils.BookStatus;

import java.util.LinkedList;
import java.util.List;

public class FilterByStatus implements FilterStrategy {

    @Override
    public List<Book> filter(List<Book> books, String value) {
        List<Book> filteredBooks = new LinkedList<>();
        BookStatus bookStatus = BookStatus.valueOf( value.toUpperCase() );
        for ( Book book : books ) {
            if ( book.getStatus() == bookStatus ) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

}
