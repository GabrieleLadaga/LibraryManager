package main.Backend.Strategy.Sort;

import main.Backend.Builder.Book;

import java.util.List;

public interface SortStrategy {

    List<Book> sort(List<Book> books);

}
