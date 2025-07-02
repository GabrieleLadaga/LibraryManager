package Strategy.Search;

import Builder.Book;
import Support.AdvancedSearchParameters;

import java.util.List;

public interface AdvancedSearchStrategy {

    List<Book> search(List<Book> books, AdvancedSearchParameters params);
}
