package main.Backend.Strategy.Search;

import main.Backend.Builder.Book;
import main.Backend.Utils.AdvancedSearchParameters;

import java.util.LinkedList;
import java.util.List;

public class AdvancedSearch implements AdvancedSearchStrategy {

    @Override
    public List<Book> search(List<Book> books, AdvancedSearchParameters params) {
        List<Book> result = new LinkedList<>();
        for(Book book : books) {
            if(matchParams(book, params)) {
                result.add(book);
            }
        }
        return result;
    }

    private boolean matchParams(Book book, AdvancedSearchParameters params) {
        return ( params.getTitle() == null || params.getTitle().equals(book.getTitle()) ) &&
               ( params.getAuthor() == null || params.getAuthor().equals(book.getAuthor()) ) &&
                ( params.getIsbn() == null || params.getIsbn().equals(book.getIsbn()) ) &&
                ( params.getStatus() == null || params.getStatus().equals(book.getStatus()) ) &&
                ( params.getGenre() == null || params.getGenre().equals(book.getGenre()) ) &&
                ( params.getRating() == null || params.getRating().equals(book.getRating()) );
    }

}
