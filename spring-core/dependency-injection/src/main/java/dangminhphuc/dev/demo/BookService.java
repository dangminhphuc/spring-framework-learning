package dangminhphuc.dev.demo;

public class BookService {
    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void addBook(Book book) {
        this.bookRepo.addBook(book);
    }

    public void listBooks() {
        this.bookRepo.listBooks().forEach(System.out::println);
    }
}
