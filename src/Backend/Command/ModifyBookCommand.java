package Backend.Command;

import Backend.Builder.Book;
import Backend.LibraryManager;

public class ModifyBookCommand implements Command {
    private final Book oldBook;
    private final Book newBook;
    private final LibraryManager libraryManager;

    public ModifyBookCommand(Book oldBook, Book newBook, LibraryManager libraryManager) {
        this.oldBook = oldBook; this.newBook = newBook; this.libraryManager = libraryManager;
    }

    @Override
    public void execute() {
        libraryManager.modifyBook(oldBook, newBook);
    }

}
