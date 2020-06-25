package socket.common.domain;

public class Book extends BaseEntity<Long> implements java.io.Serializable {
    private String title;
    private String author;
    private int price;

    public Book() {
    }

    public Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    /**
     * Get Title.
     *
     * @return the book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get Author.
     *
     * @return the book's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set Author.
     *
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get Price.
     *
     * @return the book's price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Set Price.
     *
     *
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * toString
     *
     * @return the book in String format
     */
    @Override
    public String toString() {
        return "Book{ " +
                "Title=" + title + '\'' +
                ", Author='" + author + '\'' +
                ", Price=" + price +
                "} " + super.toString();
    }
}

