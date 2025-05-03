package dangminhphuc.dev.demo.xml;

import java.util.ArrayList;
import java.util.List;

public class BookRepo {
    private List<Book> books;

    public BookRepo() {
        this.books = new ArrayList<>();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public List<Book> listBooks() {
        return this.books;
    }
}
