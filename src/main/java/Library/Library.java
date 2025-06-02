package Library;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Reader> readers;

    public Library() {
        books = new ArrayList<>();
        readers = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    public boolean removeReader(String name) {
        return readers.removeIf(reader -> reader.getName().equalsIgnoreCase(name));
    }

    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Reader> getReaders() {
        return readers;
    }
    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public void printLibraryInfo() {
        System.out.println("Книги в бібліотеці:");
        for (Book book : books) {
            System.out.println("Назва:" + book.getTitle() + ", Автор: " + book.getAuthor() +
                    ", Рік:" + book.getYear() + " (Є в наявності: " + book.isAvailable() + ")");
        }

        System.out.println("\nЧитачі бібліотеки:");
        for (Reader reader : readers) {
            System.out.print(reader.getName() + " (Позичені книги: ");
            if (reader.getBorrowedBooks().isEmpty()) {
                System.out.print("Немає");
            } else {
                for (Book book : reader.getBorrowedBooks()) {
                    System.out.print(book.getTitle() + " ");
                }
            }
            System.out.println(")");
        }
    }
}
