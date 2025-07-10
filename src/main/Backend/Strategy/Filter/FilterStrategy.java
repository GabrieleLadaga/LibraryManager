package main.Backend.Strategy.Filter;

import main.Backend.Builder.Book;
import java.util.List;

public interface FilterStrategy {

    List<Book> filter(List<Book> books, String value);

}
