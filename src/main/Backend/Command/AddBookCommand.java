package main.Backend.Command;

import main.Backend.Builder.Book;
import main.Backend.LibraryManager;

public class AddBookCommand implements Command {
    private final Book book;
    private final LibraryManager libraryManager;

    public AddBookCommand(Book book, LibraryManager libraryManager) {
        this.book = book; this.libraryManager = libraryManager;
    }

    @Override
    public void execute() {
        libraryManager.addBook(book);
    }

}
