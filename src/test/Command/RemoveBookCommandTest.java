package test.Command;

import main.Backend.Builder.Book;
import main.Backend.Command.Command;
import main.Backend.Command.RemoveBookCommand;
import main.Backend.LibraryManager;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemoveBookCommandTest {
    private LibraryManager libraryManager;
    private Book test;

    @BeforeEach
    public void setUpAll() {
        libraryManager = new LibraryManager();
        test = new Book.Builder("Il nome della rosa", "Eco", "989").build();
        libraryManager.addBook(test);
    }

    @Test
    public void testExecuteRemoveBook(){
        Command command  = new RemoveBookCommand(test, libraryManager);
        command.execute();

        assertTrue(libraryManager.getBooks().isEmpty());
    }
}
