package dangminhphuc.dev.demo.xml;

public class BookService {
    private BookRepo bookRepo;

    public BookService() {
    }

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
