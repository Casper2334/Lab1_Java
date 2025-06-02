package Library;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class LibraryServiceTest {

    private Library library;
    private LibraryService service;

    @BeforeEach
    public void setUp() {
        library = new Library();
        service = new LibraryService(library);
    }

    @Test
    public void testAddAndRemoveBook() {
        Book book = new Book("Назва", "Автор", 2020);
        library.addBook(book);
        assertTrue(library.getBooks().contains(book));

        boolean removed = library.removeBook("Назва");
        assertTrue(removed);
        assertFalse(library.getBooks().contains(book));
    }

    @Test
    public void testAddAndRemoveReader() {
        Reader reader = new Reader("Олег");
        library.addReader(reader);
        assertTrue(library.getReaders().contains(reader));

        boolean removed = library.removeReader("Олег");
        assertTrue(removed);
        assertFalse(library.getReaders().contains(reader));
    }

    @Test
    public void testBorrowBookSuccess() {
        Book book = new Book("Java", "Author", 2021);
        Reader reader = new Reader("Анна");

        library.addBook(book);
        library.addReader(reader);

        assertTrue(service.borrowBook("Анна", "Java"));
        assertFalse(book.isAvailable());
        assertTrue(reader.getBorrowedBooks().contains(book));
    }

    @Test
    public void testBorrowBookFail_bookNotAvailable() {
        Book book = new Book("Java", "Author", 2021);
        book.borrow(); // вручну робимо її недоступною
        Reader reader = new Reader("Анна");

        library.addBook(book);
        library.addReader(reader);

        assertFalse(service.borrowBook("Анна", "Java"));
    }

    @Test
    public void testReturnBookSuccess() {
        Book book = new Book("Java", "Author", 2021);
        Reader reader = new Reader("Анна");
        reader.borrowBook(book);

        library.addBook(book);
        library.addReader(reader);

        assertTrue(service.returnBook("Анна", "Java"));
        assertTrue(book.isAvailable());
        assertFalse(reader.getBorrowedBooks().contains(book));
    }

    @Test
    public void testReturnBookFail_notBorrowed() {
        Book book = new Book("Java", "Author", 2021);
        Reader reader = new Reader("Анна");

        library.addBook(book);
        library.addReader(reader);

        assertFalse(service.returnBook("Анна", "Java"));
    }

    @Test
    public void testSortBooksByTitle() {
        library.addBook(new Book("C", "A", 2000));
        library.addBook(new Book("A", "A", 2000));
        service.sortBooksByTitle();
        List<Book> sorted = library.getBooks();
        assertEquals("A", sorted.get(0).getTitle());
    }

    @Test
    public void testSortReadersByName() {
        library.addReader(new Reader("Олег"));
        library.addReader(new Reader("Андрій"));
        service.sortReadersByName();
        List<Reader> sorted = library.getReaders();
        assertEquals("Андрій", sorted.get(0).getName());
    }
    @Test
    public void testRemoveNonExistentBook() {
        boolean removed = library.removeBook("Nonexistent Book");
        assertFalse(removed, "Не можна видалити неіснуючу книгу");
    }
    @Test
    void testJsonExportImport() throws IOException {
        Library library = new Library();
        File tempFile = File.createTempFile("test", ".json");
        tempFile.deleteOnExit();

        JsonUtil.saveLibraryToFile(library, tempFile.getAbsolutePath());
        Library result = JsonUtil.loadLibraryFromFile(tempFile.getAbsolutePath());

        assertTrue(tempFile.exists(), "Файл має бути створений після експорту");
        assertNotNull(result, "Завантажена бібліотека не повинна бути null");
        assertEquals(library.getBooks().size(), result.getBooks().size(), "Кількість книг має збігатися");
        assertEquals(library.getReaders().size(), result.getReaders().size(), "Кількість читачів має збігатися");
    }

    @Test
    void testJsonImportFileNotFound() {
        assertThrows(IOException.class, () -> JsonUtil.loadLibraryFromFile("non_existing_file.json"),
                "Має кидати IOException для неіснуючого файлу");
    }
}
