package Backend.Strategy.Sort;

import Backend.Builder.Book;

import java.util.List;

public interface SortStrategy {

    List<Book> sort(List<Book> books);

}
