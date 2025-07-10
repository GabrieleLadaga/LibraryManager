package main.Backend.Strategy.Filter;

import main.Backend.Builder.Book;

import java.util.*;

public class FilterByRating implements FilterStrategy {

    @Override
    public List<Book> filter(List<Book> books, String value) {
        List<Book> filteredBooks = new LinkedList<>();
        Set<Integer> target = parseRating(value);
        for ( Book book : books ) {
            if ( target.contains( book.getRating() ) ) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    private Set<Integer> parseRating(String value) {
        Set<Integer> target = new HashSet<>();
        StringTokenizer st = new StringTokenizer(value, ",");

        while( st.hasMoreTokens() ) {
            String token = st.nextToken().trim();
            int rating = Integer.parseInt(token);
            if( rating >= 1 && rating <= 5 ) {
                target.add(rating);
            } else {
                System.err.println("Invalid rating: " + rating + " not in [1, 5]");
            }
        }
        return target;
    }

}
