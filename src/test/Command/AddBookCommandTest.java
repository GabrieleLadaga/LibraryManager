package test.Command;

import main.Backend.Builder.Book;
import main.Backend.Command.AddBookCommand;
import main.Backend.Command.Command;
import main.Backend.LibraryManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AddBookCommandTest {
    private LibraryManager libraryManager;
    private Book test;

    @BeforeEach
    public void setUpAll() {
        libraryManager = new LibraryManager();
        test = new Book.Builder("Se questo è un uomo", "Levi", "678").build();
    }

    @Test
    public void testExecuteAddBook(){
        Command command  = new AddBookCommand(test, libraryManager);
        command.execute();

        assertEquals(1, libraryManager.getBooks().size());
        assertEquals(test, libraryManager.getBooks().getFirst());
    }

}
