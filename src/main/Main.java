package main;

import main.Backend.Facade.LibraryFacade;
import main.Frontend.LibraryGUI;

public class Main {

    public static void main(String[] args) {
        LibraryFacade facede = new LibraryFacade();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new LibraryGUI(facede);
        });
    }

}
