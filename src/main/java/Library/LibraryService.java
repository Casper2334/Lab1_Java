package Library;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    public boolean borrowBook(String readerName, String bookTitle) {
        Reader reader = findReaderByName(readerName);
        Book book = findAvailableBookByTitle(bookTitle);

        if (reader != null && book != null) {
            reader.borrowBook(book);
            return true;
        }
        return false;
    }

    public boolean returnBook(String readerName, String bookTitle) {
        Reader reader = findReaderByName(readerName);
        if (reader != null) {
            for (Book borrowedBook : reader.getBorrowedBooks()) {
                if (borrowedBook.getTitle().equals(bookTitle)) {
                    reader.returnBook(borrowedBook);
                    return true;
                }
            }
        }
        return false;
    }

    private Reader findReaderByName(String name) {
        for (Reader r : library.getReaders()) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    private Book findAvailableBookByTitle(String title) {
        for (Book b : library.getBooks()) {
            if (b.getTitle().equals(title) && b.isAvailable()) {
                return b;
            }
        }
        return null;
    }

    public void sortBooksByTitle() {
        library.setBooks(library.getBooks().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList()));
    }

    public void sortBooksByAuthor() {
        library.setBooks(library.getBooks().stream()
                .sorted(Comparator.comparing(Book::getAuthor))
                .collect(Collectors.toList()));
    }

    public void sortBooksByYear() {
        library.setBooks(library.getBooks().stream()
                .sorted(Comparator.comparingInt(Book::getYear))
                .collect(Collectors.toList()));
    }

    public void sortReadersByName() {
        library.setReaders(library.getReaders().stream()
                .sorted(Comparator.comparing(Reader::getName))
                .collect(Collectors.toList()));
    }
}
