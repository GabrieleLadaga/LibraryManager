package Backend.Utils;

public record AdvancedSearchParameters( String title,
                                        String author,
                                        String isbn,
                                        BookStatus status,
                                        String genre,
                                        Integer rating ) { }
