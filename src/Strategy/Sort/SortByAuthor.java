package Strategy.Sort;

import Builder.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByAuthor implements SortStrategy {

    @Override
    public List<Book> sort(List<Book> books) {
        List<Book> sortedBook = new ArrayList<>(books);
        sortedBook.sort( new AuthorComparator() );
        return sortedBook;
    }

    static class AuthorComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            return book1.getAuthor().compareTo(book2.getAuthor());
        }

    }

}
