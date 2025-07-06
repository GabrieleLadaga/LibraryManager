package Backend.Strategy.Filter;

import Backend.Builder.Book;
import java.util.List;

public interface FilterStrategy {

    List<Book> filter(List<Book> books, String value);

}
