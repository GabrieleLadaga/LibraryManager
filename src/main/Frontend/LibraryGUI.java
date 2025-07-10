package main.Frontend;

import main.Backend.Builder.Book;
import main.Backend.Facade.LibraryFacade;
import main.Backend.Observer.LibraryObserver;
import main.Backend.Strategy.Filter.*;
import main.Backend.Strategy.Sort.*;
import main.Backend.Utils.AdvancedSearchParameters;
import main.Backend.Utils.BookStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class LibraryGUI implements LibraryObserver {
    private final LibraryFacade libraryFacade;
    private JFrame frame;
    private JTable libraryTable;

    public LibraryGUI(LibraryFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
        libraryFacade.getLibraryManager().attach(this);
        createStructure();
    }

    @Override
    public void update() {
        List<Book> currentBooks = libraryFacade.getLibraryManager().getBooks();
        updateBooksListView(currentBooks);
    }

    private void createStructure() {
        frame = new JFrame();
        frame.setTitle("Personal Library Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createTopPanel(), BorderLayout.NORTH);
        frame.add(createLibrary(), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();

        //Bottone per Menù a Sinistra
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menù");
        addItems(menu);
        menuBar.add(menu);
        panel.add(menuBar, BorderLayout.WEST);

        //Campo di Ricerca al Centro
        JTextField searchField = new JTextField(30);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            List<Book> result = libraryFacade.searchByTitle( searchField.getText() );
            updateBooksListView(result);
        });
        JButton advSearchButton = new JButton("Advanced Search");
        advSearchButton.addActionListener(e -> showAdvancedSearchDialog());
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(advSearchButton);
        panel.add(searchPanel, BorderLayout.CENTER);

        //Logo dell'App a Destra
        try {
            //Caricamento Immagine con percorso relativo
            URL imageUrl = getClass().getClassLoader().getResource("logo.jpg");
            if(imageUrl != null){
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon logoIcon = new ImageIcon(scaledImage);

                JLabel logoLabel = new JLabel(logoIcon);
                logoLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                panel.add(logoLabel, BorderLayout.EAST);
            } else {
                System.err.println("Errore: il file logo.jpg non è stato trovato nelle risorse");
            }
        }catch(Exception e) {
            System.err.println("Errore nel caricamento del logo: " + e.getMessage());
        }

        return panel;
    }

    private void addItems(JMenu menu) {
        //Add Book
        JMenuItem addBookItem = new JMenuItem("Add Book");
        addBookItem.addActionListener(e -> showAddBookDialog());
        menu.add(addBookItem);

        //Delete Book
        JMenuItem deleteBookItem = new JMenuItem("Delete Book");
        deleteBookItem.addActionListener(e -> showDeleteBookDialog());
        menu.add(deleteBookItem);

        //Modify Book
        JMenuItem modifyBookItem = new JMenuItem("Modify Book");
        modifyBookItem.addActionListener(e -> showModifyBookDialog());
        menu.add(modifyBookItem);

        //Sort Books
        JMenuItem sortBooksItem = new JMenuItem("Sort Book");
        sortBooksItem.addActionListener(e -> showSortBooksDialog());
        menu.add(sortBooksItem);

        //Filter Book
        JMenuItem filterBooksItem = new JMenuItem("Filter Books");
        filterBooksItem.addActionListener(e -> showFilterBooksDialog());
        menu.add(filterBooksItem);
    }

    private JScrollPane createLibrary() {
        //Set-Up della Tabella
        String[] columnNames = {"Title", "Author", "Genre", "Status", "Rating", "ISBN"};
        Object[][] data = getBooksFromFacade();

        libraryTable = new JTable(data, columnNames);
        return new JScrollPane(libraryTable);
    }

    private Object[][] getBooksFromFacade() {
        List<Book> currentBooks = libraryFacade.getLibraryManager().getBooks();
        Object[][] data = new Object[currentBooks.size()][6];
        int index = 0;
        for(Book book : currentBooks) {
            data[index++] = new Object[]{
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getStatus(),
                    book.getRating(),
                    book.getIsbn()
            };
        }
        return data;
    }

    private void showAdvancedSearchDialog() {
        //Set-Up Dialog
        JDialog advancedSearchDialog = new JDialog(frame, "Advanced Search", true);
        advancedSearchDialog.setLayout(new BorderLayout(10, 10));
        advancedSearchDialog.setSize(400, 300);
        advancedSearchDialog.setLocationRelativeTo(frame);

        //Pannello Principale
        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Fields
        JTextField titleFiled = new JTextField();
        JTextField authorFiled = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField genreFiled = new JTextField();
        JTextField statusFiled = new JTextField();
        JTextField ratingFiled = new JTextField();

        //Aggiunta dei Campi al pannello
        fieldsPanel.add(new JLabel("Title:"));
        fieldsPanel.add(titleFiled);
        fieldsPanel.add(new JLabel("Author:"));
        fieldsPanel.add(authorFiled);
        fieldsPanel.add(new JLabel("ISBN:"));
        fieldsPanel.add(isbnField);
        fieldsPanel.add(new JLabel("Genre:"));
        fieldsPanel.add(genreFiled);
        fieldsPanel.add(new JLabel("Status:"));
        fieldsPanel.add(statusFiled);
        fieldsPanel.add(new JLabel("Rating (1-5):"));
        fieldsPanel.add(ratingFiled);

        //Pannello dei Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10 ,10));

        //Buttons usati
        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchButton.addActionListener( e -> {
            //Identificazione dei parametri per la ricerca avanzata
            AdvancedSearchParameters advancedSearchParameters = new AdvancedSearchParameters();
            if( !titleFiled.getText().isEmpty() ){
                advancedSearchParameters.setTitle(titleFiled.getText());
            }
            if( !authorFiled.getText().isEmpty() ){
                advancedSearchParameters.setAuthor(authorFiled.getText());
            }
            if( !isbnField.getText().isEmpty() ){
                advancedSearchParameters.setIsbn(isbnField.getText());
            }
            if( !genreFiled.getText().isEmpty() ){
                advancedSearchParameters.setGenre(genreFiled.getText());
            }
            if( !statusFiled.getText().isEmpty() ){
                try{
                    advancedSearchParameters.setStatus(BookStatus.valueOf(statusFiled.getText()));
                }catch (IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(advancedSearchDialog, "Invalid status", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if( !ratingFiled.getText().isEmpty() ){
                try{
                    int rating = Integer.parseInt(ratingFiled.getText());
                    if( rating < 1 || rating > 5 ){
                        throw new IllegalArgumentException();
                    }
                    advancedSearchParameters.setRating(rating);
                }catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(advancedSearchDialog, "Invalid number rating", "Error", JOptionPane.ERROR_MESSAGE);
                }catch (IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(advancedSearchDialog, "Invalid argument rating", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            List<Book> result = libraryFacade.advancedSearch(advancedSearchParameters);
            updateBooksListView(result);
            advancedSearchDialog.dispose();
        });

        cancelButton.addActionListener( e -> advancedSearchDialog.dispose() );

        //Aggiunta dei Bottoni al Pannello
        buttonsPanel.add(searchButton);
        buttonsPanel.add(cancelButton);

        //Aggiunta dei pannelli al Dialog
        advancedSearchDialog.add(fieldsPanel, BorderLayout.CENTER);
        advancedSearchDialog.add(buttonsPanel, BorderLayout.SOUTH);

        advancedSearchDialog.setVisible(true);
    }

    private void showAddBookDialog() {
        //Set-Up Dialog
        JDialog addDialog = new JDialog(frame, "Add Book", true);
        addDialog.setLayout(new BorderLayout(10, 10));
        addDialog.setSize(400, 300);
        addDialog.setLocationRelativeTo(frame);

        //Pannello Principale
        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Creazione Campi Obbligatori
        JTextField titleFiled = new JTextField();
        JTextField authorFiled = new JTextField();
        JTextField isbnField = new JTextField();

        //Creazione Campi Opzionali
        JTextField genreFiled = new JTextField();
        JTextField statusFiled = new JTextField();
        JTextField ratingFiled = new JTextField();

        //Aggiunta dei Campi al pannello
        fieldsPanel.add(new JLabel("Title*:"));
        fieldsPanel.add(titleFiled);
        fieldsPanel.add(new JLabel("Author*:"));
        fieldsPanel.add(authorFiled);
        fieldsPanel.add(new JLabel("ISBN*:"));
        fieldsPanel.add(isbnField);
        fieldsPanel.add(new JLabel("Genre:"));
        fieldsPanel.add(genreFiled);
        fieldsPanel.add(new JLabel("Status:"));
        fieldsPanel.add(statusFiled);
        fieldsPanel.add(new JLabel("Rating (1-5):"));
        fieldsPanel.add(ratingFiled);

        //Pannello dei Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10 ,10));

        //Buttons usati
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        //Click su Bottone di Conferma (Confirm)
        confirmButton.addActionListener(e -> {
            if( titleFiled.getText().isEmpty() || authorFiled.getText().isEmpty() || isbnField.getText().isEmpty() ) {
                JOptionPane.showMessageDialog(addDialog, "Please, fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE );
                return;
            }
            //Builder del libro con campi obbligatori
            Book.Builder bookBuilder = new Book.Builder( titleFiled.getText(), authorFiled.getText(), isbnField.getText() );

            //Gestione Option Fields
            if( !genreFiled.getText().isEmpty() ){
                bookBuilder.genre( genreFiled.getText() );
            }
            BookStatus status = null;
            if( !statusFiled.getText().isEmpty() ) {
                try {
                    status = BookStatus.valueOf( statusFiled.getText().toUpperCase() );
                }catch(IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(addDialog, "Invalid status! Use: 'Da Leggere', 'In Lettura' or 'Letto'", "Error", JOptionPane.ERROR_MESSAGE );
                    return;
                }
            }
            bookBuilder.status(status);
            if( !ratingFiled.getText().isEmpty() ) {
                try {
                    int rating = Integer.parseInt(ratingFiled.getText());
                    if (rating >= 1 && rating <= 5) {
                        bookBuilder.rating(rating);
                    } else {
                        JOptionPane.showMessageDialog(addDialog, "Rating not in range [1, 5]!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(addDialog, "Please enter a valid number for rating!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            //Gestione nuovo libro
            libraryFacade.addBook(bookBuilder.build());
            addDialog.dispose();
        });

        //Click sul bottone di Annullamento (Cancel)
        cancelButton.addActionListener(e -> addDialog.dispose() );

        //Aggiunta dei Bottoni al Pannello
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);

        //Aggiunta dei pannelli al Dialog
        addDialog.add(fieldsPanel, BorderLayout.CENTER);
        addDialog.add(buttonsPanel, BorderLayout.SOUTH);

        addDialog.setVisible(true);
    }


    private void showDeleteBookDialog() {
        //Set-Up Dialog
        JDialog deleteDialog = new JDialog(frame, "Delete Book", true);
        deleteDialog.setLayout(new BorderLayout());
        deleteDialog.setSize(300, 300);
        deleteDialog.setLocationRelativeTo(frame);

        //Pannello Principale
        JPanel selectPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JScrollPane scrollPane = new JScrollPane(selectPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Creazione gruppo di bottoni
        ButtonGroup groupButton = new ButtonGroup();

        List<Book> currentBooks = libraryFacade.getLibraryManager().getBooks();

        for(Book book : currentBooks) {
            JRadioButton button = new JRadioButton();
            JLabel title = new JLabel(book.getTitle());

            button.putClientProperty("book", book);

            //Aggiunta di ogni libro al gruppo di bottoni
            groupButton.add(button);
            selectPanel.add(button);
            selectPanel.add(title);
        }

        //Pannello dei Bottoni
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        //Click sul bottone di Eliminazione (Delete)
        deleteButton.addActionListener(e -> {
            for(Component component: selectPanel.getComponents()) {
                if(component instanceof JRadioButton && ((JRadioButton) component).isSelected()) {
                    Book selectedBook = (Book) ((JRadioButton) component).getClientProperty("book");
                    libraryFacade.removeBook(selectedBook);
                    deleteDialog.dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(deleteDialog, "Nothing book is selected!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        //Click sul bottone di Annullamento (Cancel)
        cancelButton.addActionListener(e -> deleteDialog.dispose() );

        //Aggiunta dei bottoni al Pannello
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        //Aggiunta dei pannelli al Dialog
        deleteDialog.add(scrollPane, BorderLayout.CENTER);
        deleteDialog.add(buttonPanel, BorderLayout.SOUTH);

        deleteDialog.pack(); //??

        deleteDialog.setVisible(true);
    }

    private void showModifyBookDialog() {
        //Dialog per la selezione del libro
        JDialog selectDialog = new JDialog(frame, "Select Book to Modify", true);
        selectDialog.setLayout(new BorderLayout(10, 10));
        selectDialog.setSize(400, 300);
        selectDialog.setLocationRelativeTo(frame);

        //Pannello Principale per i Radio Button
        JPanel radioPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JScrollPane scrollPane = new JScrollPane(radioPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ButtonGroup bookGroup = new ButtonGroup();
        List<Book> currentBooks = libraryFacade.getLibraryManager().getBooks();

        //Inserimento dei book nel bookGroup
        for(Book book : currentBooks) {
            JRadioButton button = new JRadioButton();
            JLabel title = new JLabel(book.getTitle());

            button.putClientProperty("book", book);

            bookGroup.add(button);
            radioPanel.add(button);
            radioPanel.add(title);
        }

        //Pannello per i Bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton nextButton = new JButton("Next");
        JButton cancelButton = new JButton("Cancel");

        nextButton.addActionListener(e -> {
            for(Component component: radioPanel.getComponents()) {
                if(component instanceof JRadioButton && ((JRadioButton) component).isSelected()) {
                    Book selectedBook = (Book) ((JRadioButton) component).getClientProperty("book");
                    selectDialog.dispose();
                    showModifyItemsBookDialog(selectedBook);
                    return;
                }
            }
            JOptionPane.showMessageDialog(selectDialog, "Nothing book is selected!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        cancelButton.addActionListener(e -> selectDialog.dispose() );

        //Aggiunta dei Bottoni al Panel
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        selectDialog.add(scrollPane, BorderLayout.CENTER);
        selectDialog.add(buttonPanel, BorderLayout.SOUTH);

        selectDialog.setVisible(true);
    }

    private void showModifyItemsBookDialog(Book oldBook) {
        //Set-Up Dialog per modifica dei dettagli
        JDialog modifyDialog = new JDialog(frame, "Modify Book Details", true);
        modifyDialog.setLayout(new BorderLayout(10, 10));
        modifyDialog.setSize(400, 300);
        modifyDialog.setLocationRelativeTo(frame);

        //Pannello per i campi di testo
        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Mandatory Fields
        JTextField titleFiled = new JTextField(oldBook.getTitle());
        JTextField authorFiled = new JTextField(oldBook.getAuthor());
        JTextField isbnField = new JTextField(oldBook.getIsbn());
        isbnField.setEditable(false);

        //Optional Fields
        JTextField genreFiled = new JTextField(oldBook.getGenre() != null ? oldBook.getGenre() : "");
        JTextField statusFiled = new JTextField(oldBook.getStatus() != null ? oldBook.getStatus().toString() : "");
        JTextField ratingFiled = new JTextField(oldBook.getRating() != null ? oldBook.getRating().toString() : "");

        //Aggiunta dei Campi al Pannello
        fieldsPanel.add(new JLabel("Title*:"));
        fieldsPanel.add(titleFiled);
        fieldsPanel.add(new JLabel("Author*:"));
        fieldsPanel.add(authorFiled);
        fieldsPanel.add(new JLabel("ISBN*:"));
        fieldsPanel.add(isbnField);
        fieldsPanel.add(new JLabel("Genre:"));
        fieldsPanel.add(genreFiled);
        fieldsPanel.add(new JLabel("Status ('Da Leggere'/'In Lettura'/'Letto'):"));
        fieldsPanel.add(statusFiled);
        fieldsPanel.add(new JLabel("Rating (1-5):"));
        fieldsPanel.add(ratingFiled);

        //Pannello dei Bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));


        //Buttons
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        //Click su Save Book
        saveButton.addActionListener(e -> {
            //Validazione
            if( titleFiled.getText().isEmpty() || authorFiled.getText().isEmpty() ) {
                JOptionPane.showMessageDialog(modifyDialog, "Please, fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE );
                return;
            }

            //Builder del libro con campi obbligatori
            Book.Builder bookBuilder = new Book.Builder( titleFiled.getText(), authorFiled.getText(), isbnField.getText() );

            //Gestione Option Fields
            if( !genreFiled.getText().isEmpty() ){
                bookBuilder.genre( genreFiled.getText() );
            }
            if( ! statusFiled.getText().isEmpty() ) {
                try {
                    bookBuilder.status( BookStatus.valueOf( statusFiled.getText().toUpperCase() ) );
                }catch(IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(modifyDialog, "Invalid status! Use: 'Da Leggere', 'In Lettura' or 'Letto'", "Error", JOptionPane.ERROR_MESSAGE );
                    return;
                }
            }
            if( !ratingFiled.getText().isEmpty() ) {
                try{
                    int rating = Integer.parseInt( ratingFiled.getText() );
                    if( rating >= 1 && rating <= 5 ) {
                        bookBuilder.rating( rating );
                    }
                }catch(NumberFormatException exception){
                    JOptionPane.showMessageDialog(modifyDialog, "Not in range [1, 5]!", "Error", JOptionPane.ERROR_MESSAGE );
                    return;
                }
            }

            //Gestione nuovo libro
            libraryFacade.modifyBook(oldBook, bookBuilder.build());
            modifyDialog.dispose();
        });

        //Click su Cancel Button
        cancelButton.addActionListener(e -> modifyDialog.dispose() );

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        //Aggiunta dei Panelli al Dialog
        modifyDialog.add(fieldsPanel, BorderLayout.CENTER);
        modifyDialog.add(buttonPanel, BorderLayout.SOUTH);
        modifyDialog.setVisible(true);
    }

    private void showSortBooksDialog() {
        JDialog sortDialog = new JDialog(frame, "Sort Books", true);
        sortDialog.setLayout(new BorderLayout(10,10));
        sortDialog.setSize(350, 350);
        sortDialog.setLocationRelativeTo(frame);

        //Pannello Principale
        JPanel optionsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Aggiunta Scroll Pane
        JScrollPane scroll = new JScrollPane(optionsPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        ButtonGroup sortGroup = new ButtonGroup();

        //Mappa le opzioni alle corrispondenti strategie
        Map<String, SortStrategy> sortOptions = Map.of( "By Title", new SortByTitle(),
                "By Author", new SortByAuthor(),
                "By ISBN", new SortByIsbn(),
                "By Genre", new SortByGenre(),
                "By Status", new SortByStatus(),
                "By Rating", new SortByRating() );

        for(String option : sortOptions.keySet() ) {
            JRadioButton radioButton = new JRadioButton();
            JLabel title = new JLabel(option);

            radioButton.putClientProperty("sortOption", option);

            sortGroup.add(radioButton);
            optionsPanel.add(radioButton);
            optionsPanel.add(title);
        }

        //Pannello per i Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            for(Component component: optionsPanel.getComponents()) {
                if(component instanceof JRadioButton && ((JRadioButton) component).isSelected()) {
                    //Prendo la scelta e imposto il tipo di sort
                    String selectedOption = (String) ((JRadioButton) component).getClientProperty("sortOption");
                    libraryFacade.setSortStrategy(sortOptions.get(selectedOption));

                    //Applica l'ordinamento e aggiorna la vista
                    updateBooksListView(libraryFacade.getSortedBook());
                    sortDialog.dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(sortDialog, "Nothing option is selected!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        cancelButton.addActionListener(e -> sortDialog.dispose() );

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        sortDialog.add(scroll, BorderLayout.CENTER);
        sortDialog.add(buttonPanel, BorderLayout.SOUTH);
        sortDialog.setVisible(true);
    }

    private void showFilterBooksDialog() {
        JDialog filterDialog = new JDialog(frame, "Select Filter Type", true);
        filterDialog.setLayout(new BorderLayout(10,10));
        filterDialog.setSize(350, 350);
        filterDialog.setLocationRelativeTo(frame);

        //Pannello Principale
        JPanel optionsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Aggiunta Scroll Pane
        JScrollPane scroll = new JScrollPane(optionsPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        ButtonGroup sortGroup = new ButtonGroup();

        //Mappa le opzioni alle corrispondenti strategie
        Map<String, FilterStrategy> filterOptions = Map.of( "By Title", new FilterByTitle(),
                "By Author", new FilterByAuthor(),
                "By Genre", new FilterByGenre(),
                "By Status", new FilterByStatus(),
                "By Rating", new FilterByRating() );

        for(String option : filterOptions.keySet() ) {
            JRadioButton radioButton = new JRadioButton();
            JLabel title = new JLabel(option);

            radioButton.putClientProperty("filterOption", option);

            sortGroup.add(radioButton);
            optionsPanel.add(radioButton);
            optionsPanel.add(title);
        }

        //Pannello per i Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton nextButton = new JButton("Next");
        JButton cancelButton = new JButton("Cancel");

        nextButton.addActionListener(e -> {
            for(Component component: optionsPanel.getComponents()) {
                if(component instanceof JRadioButton && ((JRadioButton) component).isSelected()) {
                    //Prendo la scelta e imposto il tipo di sort
                    String selectedOption = (String) ((JRadioButton) component).getClientProperty("filterOption");
                    libraryFacade.setFilterStrategy(filterOptions.get(selectedOption));

                    //Passo al Dialog per il valore del filtro
                    filterDialog.dispose();
                    showFilterValueDialog(selectedOption);
                    return;
                }
            }
            JOptionPane.showMessageDialog(filterDialog, "Nothing option is selected!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        cancelButton.addActionListener(e -> filterDialog.dispose() );

        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        filterDialog.add(scroll, BorderLayout.CENTER);
        filterDialog.add(buttonPanel, BorderLayout.SOUTH);
        filterDialog.setVisible(true);
    }

    private void showFilterValueDialog(String filterType) {
        JDialog filterValueDialog = new JDialog(frame, "Select Filter Type", true);
        filterValueDialog.setLayout(new BorderLayout(10,10));
        filterValueDialog.setSize(350, 150);
        filterValueDialog.setLocationRelativeTo(frame);

        //Pannello per input
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel valueLabel = new JLabel("Value:");
        JTextField valueField = new JTextField();

        inputPanel.add(valueLabel);
        inputPanel.add(valueField);

        //Pannello per i Bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton okButton = new JButton("OK");
        JButton backButton = new JButton("Back");

        okButton.addActionListener(e -> {
            String filterValue = valueField.getText().trim();
            if( !filterValue.isEmpty() ) {
                updateBooksListView(libraryFacade.getFilterBook(filterValue));
                filterValueDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(filterValueDialog, "Please enter a filter value!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            filterValueDialog.dispose();
            showFilterBooksDialog(); //Ritorno al Dialog precedente
        });

        buttonPanel.add(okButton);
        buttonPanel.add(backButton);

        filterValueDialog.add(inputPanel, BorderLayout.CENTER);
        filterValueDialog.add(buttonPanel, BorderLayout.SOUTH);
        filterValueDialog.setVisible(true);
    }

    private void updateBooksListView(List<Book> books) {

        String[] columnNames = {"Title", "Author", "Genre", "Status", "Rating", "ISBN"};
        Object[][] data = new Object[books.size()][6];

        int index = 0;
        for(Book book : books) {
            data[index++] = new Object[] {
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getStatus(),
                    book.getRating(),
                    book.getIsbn()
            };
        }

        //Aggiorna il Modello della tabella esistente
        libraryTable.setModel(new DefaultTableModel(data, columnNames));

        //Aggiornamento dell'interfaccia
        frame.revalidate();
        frame.repaint();
    }

}
