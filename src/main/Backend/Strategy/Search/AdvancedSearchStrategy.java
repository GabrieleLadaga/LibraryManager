package main.Backend.Strategy.Search;

import main.Backend.Builder.Book;
import main.Backend.Utils.AdvancedSearchParameters;

import java.util.List;

public interface AdvancedSearchStrategy {

    List<Book> search(List<Book> books, AdvancedSearchParameters params);
}
