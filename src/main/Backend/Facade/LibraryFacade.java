package main.Backend.Facade;

import main.Backend.Builder.Book;
import main.Backend.Command.*;
import main.Backend.LibraryManager;
import main.Backend.Strategy.Filter.FilterByTitle;
import main.Backend.Strategy.Filter.FilterStrategy;
import main.Backend.Strategy.Search.AdvancedSearch;
import main.Backend.Strategy.Search.AdvancedSearchStrategy;
import main.Backend.Strategy.Search.SearchByTitle;
import main.Backend.Strategy.Search.SearchStrategy;
import main.Backend.Strategy.Sort.SortByTitle;
import main.Backend.Strategy.Sort.SortStrategy;
import main.Backend.Utils.AdvancedSearchParameters;

import java.util.List;

public class LibraryFacade {
    private final LibraryCommandInvoker libraryCommandInvoker;
    private final LibraryManager libraryManager;
    private SortStrategy sortStrategy;
    private FilterStrategy filterStrategy;
    private final SearchStrategy searchStrategy;
    private final AdvancedSearchStrategy advancedSearchStrategy;

    public LibraryFacade() {
        libraryCommandInvoker = new LibraryCommandInvoker();
        libraryManager = new LibraryManager();
        sortStrategy = new SortByTitle();
        filterStrategy = new FilterByTitle();
        searchStrategy = new SearchByTitle();
        advancedSearchStrategy = new AdvancedSearch();
    }

    public void addBook(Book book) {
        Command command = new AddBookCommand(book, libraryManager);
        libraryCommandInvoker.executeCommand(command);
    }

    public void removeBook(Book book) {
        Command command = new RemoveBookCommand(book, libraryManager);
        libraryCommandInvoker.executeCommand(command);
    }

    public void modifyBook(Book oldBook, Book newBook) {
        Command command = new ModifyBookCommand(oldBook, newBook, libraryManager);
        libraryCommandInvoker.executeCommand(command);
    }

    public List<Book> searchByTitle(String title) {
        return searchStrategy.search(libraryManager.getBooks(), title);
    }

    public List<Book> advancedSearch(AdvancedSearchParameters advancedSearch) {
        return advancedSearchStrategy.search(libraryManager.getBooks(), advancedSearch);
    }

    public List<Book> getSortedBook() {
        return sortStrategy.sort(libraryManager.getBooks());
    }

    public List<Book> getFilterBook(String filterValue) {
        return filterStrategy.filter(libraryManager.getBooks(), filterValue);
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void setFilterStrategy(FilterStrategy filterStrategy) {
        this.filterStrategy = filterStrategy;
    }

    public LibraryManager getLibraryManager() {
        return libraryManager;
    }

}
