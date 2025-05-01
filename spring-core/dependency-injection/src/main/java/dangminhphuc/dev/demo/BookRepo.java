package dangminhphuc.dev.demo;

import java.util.ArrayList;
import java.util.List;

public class BookRepo {
    private final List<Book> books;

    public BookRepo() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public List<Book> listBooks() {
        return this.books;
    }
}
