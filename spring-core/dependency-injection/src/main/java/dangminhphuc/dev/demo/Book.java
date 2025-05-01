package dangminhphuc.dev.demo;

public class Book {
    private final String title;
    private final String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" + "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
