package Backend.Strategy.Search;

import Backend.Builder.Book;
import Backend.Utils.AdvancedSearchParameters;

import java.util.List;

public interface AdvancedSearchStrategy {

    List<Book> search(List<Book> books, AdvancedSearchParameters params);
}
