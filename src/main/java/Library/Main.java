package Library;

import java.util.Scanner;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryService service = new LibraryService(library);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Меню бібліотеки ===");
            System.out.println("1. Додати книгу");
            System.out.println("2. Додати читача");
            System.out.println("3. Взяти книгу");
            System.out.println("4. Повернути книгу");
            System.out.println("5. Зберегти в файл");
            System.out.println("6. Завантажити з файлу");
            System.out.println("7. Вивести інформацію про бібліотеку");
            System.out.println("8. Сортувати книги за назвою");
            System.out.println("9. Сортувати книги за автором");
            System.out.println("10. Сортувати книги за роком видання");
            System.out.println("11. Сортувати читачів за іменем");
            System.out.println("12. Видалити книгу");
            System.out.println("13. Видалити читача");
            System.out.println("14. Вийти");
            System.out.print("Ваш вибір: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Назва книги: ");
                    String title = scanner.nextLine();
                    System.out.print("Автор: ");
                    String author = scanner.nextLine();
                    System.out.print("Рік видання: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    if(year < 0){
                        System.out.print("Рік видання не може бути від'ємним");
                        break;
                    }
                    Book book = new Book(title, author, year);
                    library.addBook(book);
                    System.out.println("Книгу додано.");
                }
                case "2" -> {
                    System.out.print("Ім'я читача: ");
                    String name = scanner.nextLine();
                    Reader reader = new Reader(name);
                    library.addReader(reader);
                    System.out.println("Читача додано.");
                }
                case "3" -> {
                    System.out.print("Ім'я читача: ");
                    String readerName = scanner.nextLine();
                    System.out.print("Назва книги: ");
                    String bookTitle = scanner.nextLine();
                    if (service.borrowBook(readerName, bookTitle)) {
                        System.out.println("Книгу видано.");
                    } else {
                        System.out.println("Не вдалося видати книгу.");
                    }
                }
                case "4" -> {
                    System.out.print("Ім'я читача: ");
                    String readerName = scanner.nextLine();
                    System.out.print("Назва книги: ");
                    String bookTitle = scanner.nextLine();
                    if (service.returnBook(readerName, bookTitle)) {
                        System.out.println("Книгу повернено.");
                    } else {
                        System.out.println("Не вдалося повернути книгу.");
                    }
                }
                case "5" -> {
                    try {
                        JsonUtil.saveLibraryToFile(library, "library.json");
                        System.out.println("Бібліотеку збережено.");
                    } catch (IOException e) {
                        System.out.println("Помилка збереження: " + e.getMessage());
                    }
                }
                case "6" -> {
                    try {
                        Library loaded = JsonUtil.loadLibraryFromFile("library.json");
                        if (loaded != null) {
                            library = loaded;
                            service = new LibraryService(library);
                            System.out.println("Бібліотеку завантажено.");
                        } else {
                            System.out.println("Файл пошкоджений або порожній.");
                        }
                    } catch (IOException e) {
                        System.out.println("Помилка завантаження: " + e.getMessage());
                    }
                }


                case "7" -> {
                    library.printLibraryInfo();
                }
                case "8" -> {
                    service.sortBooksByTitle();
                    System.out.println("Книги відсортовано за назвою.");
                }
                case "9" -> {
                    service.sortBooksByAuthor();
                    System.out.println("Книги відсортовано за автором.");
                }
                case "10" -> {
                    service.sortBooksByYear();
                    System.out.println("Книги відсортовано за роком.");
                }
                case "11" -> {
                    service.sortReadersByName();
                    System.out.println("Читачів відсортовано за іменем.");
                }
                case "12" -> {
                    System.out.print("Введіть назву книги для видалення: ");
                    String title = scanner.nextLine();
                    if (library.removeBook(title)) {
                        System.out.println("Книгу видалено.");
                    } else {
                        System.out.println("Книгу не знайдено.");
                    }
                }
                case "13" -> {
                    System.out.print("Введіть ім'я читача для видалення: ");
                    String name = scanner.nextLine();
                    if (library.removeReader(name)) {
                        System.out.println("Читача видалено.");
                    } else {
                        System.out.println("Читача не знайдено.");
                    }
                }
                case "14" -> {
                    System.out.println("Завершення роботи...");
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
}
