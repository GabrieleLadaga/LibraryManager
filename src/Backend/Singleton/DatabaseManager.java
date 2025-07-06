package Backend.Singleton;

import Backend.Builder.Book;
import Backend.Utils.BookStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final String DATABASE_URL = "jdbc:sqlite:database/libreria.db";
    private static DatabaseManager instance;
    private static Connection connection;

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            createTable();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public synchronized static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void createTable() throws SQLException {
        String sql_query = " CREATE TABLE IF NOT EXISTS books (" +
                           " isbn TEXT PRIMARY KEY, " +
                           " title TEXT NOT NULL, " +
                           " author TEXT NOT NULL, " +
                           " genre TEXT, " +
                           " rating INTEGER CHECK (rating BETWEEN 1 AND 5), " +
                           " status TEXT CHECK (status IN ('DA_LEGGERE', 'IN_LETTURA', 'LETTO')) )";
        try (Statement statement = connection.createStatement()) {
            statement.execute( sql_query );
        }
    }

    public void addBook(Book book) throws SQLException {
        String sql_query = " INSERT INTO books(isbn, title, author, genre, rating, status) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getGenre());
            preparedStatement.setInt(5, book.getRating());
            preparedStatement.setString(6, book.getStatus().name());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteBook(Book book) throws SQLException {
        String sql_query = " DELETE FROM books WHERE isbn = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.executeUpdate();
        }
    }

    public void updateBook(Book book) throws SQLException {
        String sql_query = " UPDATE books" +
                           " SET title = ?, author = ?, genre = ?, rating = ?, status = ? " +
                           " WHERE isbn = ? ";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getRating());
            preparedStatement.setString(5, book.getStatus().name());
            preparedStatement.setString(6, book.getIsbn());
            preparedStatement.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql_query = " SELECT * FROM books";

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery( sql_query );
            while (resultSet.next()) {
                Book book = new Book.Builder(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("isbn")
                )
                        .genre(resultSet.getString("genre"))
                        .status(BookStatus.valueOf(resultSet.getString("status")))
                        .rating(resultSet.getInt("rating"))
                        .build();
                books.add(book);
            }
        }
        return books;
    }

}
