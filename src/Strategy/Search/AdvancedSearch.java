package Strategy.Search;

import Builder.Book;
import Support.AdvancedSearchParameters;

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
        return ( params.title() == null || params.title().equals(book.getTitle()) ) &&
               ( params.author() == null || params.author().equals(book.getAuthor()) ) &&
                ( params.isbn() == null || params.isbn().equals(book.getIsbn()) ) &&
                ( params.status() == null || params.status().equals(book.getStatus()) ) &&
                ( params.genre() == null || params.genre().equals(book.getGenre()) ) &&
                ( params.rating() == null || params.rating().equals(book.getRating()) );
    }

}
