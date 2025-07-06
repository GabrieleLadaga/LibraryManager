package Backend.Strategy.Search;

import Backend.Builder.Book;

import java.util.LinkedList;
import java.util.List;

public class SearchByTitle implements SearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String query) {
        List<Book> result = new LinkedList<>();
        for (Book book : books) {
            if(book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

}
