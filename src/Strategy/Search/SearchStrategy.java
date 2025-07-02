package Strategy.Search;

import Builder.Book;

import java.util.List;

public interface SearchStrategy {

    List<Book> search(List<Book> books, String query);

}
