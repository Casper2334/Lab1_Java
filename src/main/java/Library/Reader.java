package Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader {
    private String name;
    private List<Book> borrowedBooks;

    public Reader() {
    }

    public Reader(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }


    public boolean borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.borrow();
            return true;
        }
        return false;
    }


    public boolean returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.returnBook();
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return name.equals(reader.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return "Reader{" + "name='" + name + '\'' + ", borrowedBooks=" + borrowedBooks + '}';
    }
}
