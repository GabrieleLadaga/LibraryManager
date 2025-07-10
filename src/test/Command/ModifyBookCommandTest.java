package test.Command;

import main.Backend.Builder.Book;
import main.Backend.Command.Command;
import main.Backend.Command.ModifyBookCommand;
import main.Backend.LibraryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModifyBookCommandTest {
    private LibraryManager libraryManager;
    private Book test;

    @BeforeEach
    public void setUpAll() {
        libraryManager = new LibraryManager();
        test = new Book.Builder("Il Ritratto di Dorian Grey", "Boccaccio", "496").build();
        libraryManager.addBook(test);
    }

    @Test
    public void testExecuteModifyBook(){
        Book correctBook = new Book.Builder("Il Decameron", test.getAuthor(), test.getIsbn()).build();
        Command command  = new ModifyBookCommand(test, correctBook, libraryManager);
        command.execute();

        assertEquals(1, libraryManager.getBooks().size());
        assertEquals(correctBook, libraryManager.getBooks().getFirst());

        assertFalse(libraryManager.getBooks().getFirst().equals(test));
    }

}
