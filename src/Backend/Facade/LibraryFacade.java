package Backend.Facade;

import Backend.Builder.Book;
import Backend.Command.*;
import Backend.LibraryManager;
import Backend.Strategy.Filter.FilterByTitle;
import Backend.Strategy.Filter.FilterStrategy;
import Backend.Strategy.Search.AdvancedSearch;
import Backend.Strategy.Search.AdvancedSearchStrategy;
import Backend.Strategy.Search.SearchByTitle;
import Backend.Strategy.Search.SearchStrategy;
import Backend.Strategy.Sort.SortByTitle;
import Backend.Strategy.Sort.SortStrategy;
import Backend.Utils.AdvancedSearchParameters;

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

    public void searchByTitle(String title) {
        searchStrategy.search(libraryManager.getBooks(), title);
    }

    public void advancedSearch(AdvancedSearchParameters advancedSearch) {
        advancedSearchStrategy.search(libraryManager.getBooks(), advancedSearch);
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

}
