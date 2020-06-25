package repository_2.ui;

import com.sun.jdi.BooleanValue;
import javafx.util.Pair;
import org.xml.sax.SAXException;
import repository_2.domain.BaseEntity;
import repository_2.domain.Book;
import repository_2.domain.Client;
import repository_2.domain.Transaction;
import repository_2.domain.validators.ValidatorException;
import repository_2.service.BookService;
import repository_2.service.ClientService;
import repository_2.service.TransactionService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Console {
    private BookService bookService;
    private ClientService clientService;
    private TransactionService transactionService;

    public Console(BookService bookService, ClientService clientService, TransactionService transactionService) {
        this.bookService = bookService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    private void printMenuFilter() {
        System.out.println("MENU FOR FILTERS");
        System.out.println("1. Filter books by author.");
        System.out.println("2. Filter clients who are greater than given age.");
        System.out.println("3. Filter transactions made in given year.");
        System.out.println("0. Back to main menu.");
    }

    private void printMenuReports() {
        System.out.println("MENU FOR REPORTS");
        System.out.println("1. Sort books descending by their price.");
        System.out.println("2. Sort clients ascending by their age.");
        System.out.println("3. Show all clients who bought books by author2.");
        System.out.println("0. Back to main menu.");
    }

    private void printMenu(){
        System.out.println("MENU");
        System.out.println("1. Add book.");
        System.out.println("2. Update a book.");
        System.out.println("3. Delete a book.");
        System.out.println("4. List all books.");
        System.out.println("\n");

        System.out.println("5. Add client.");
        System.out.println("6. Update a client.");
        System.out.println("7. Delete a client.");
        System.out.println("8. List all clients.");
        System.out.println("\n");

        System.out.println("9. Buy a book. (Add a transaction)");
        System.out.println("10. Update a transaction.");
        System.out.println("11. Delete a transaction.");
        System.out.println("12. List all transactions.");
        System.out.println("\n");

        System.out.println("13. Enter filter menu.");
        System.out.println("14. Enter reports menu.");
        System.out.println("\n");

        System.out.println("0. Exit.\n");
    }

    private void runFilter() throws IOException {
        printMenuFilter();

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String option = bufferRead.readLine();
            while (!option.equals("0")) {
                switch (option) {
                    case "1":
                        filterBooksByAuthor();
                        break;
                    case "2":
                        filterClientsByAge();
                        break;
                    case "3":
                        filterTransactionsByDate();
                        break;
                    default:
                        System.out.println("This option is invalid.");
                }
                printMenuFilter();
                option = bufferRead.readLine();
            }
            runConsole();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void runReports() throws IOException {
        printMenuReports();

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String option = bufferRead.readLine();
            while (!option.equals("0")) {
                switch (option) {
                    case "1":
                        sortBookDescendingByPrice();
                        break;
                    case "2":
                        sortClientAscendingByAge();
                        break;
                    case "3":
                        clientsWhoBoughtByAuthor2();
                        break;
                    default:
                        System.out.println("This option is invalid.");
                }
                printMenuReports();
                option = bufferRead.readLine();
            }
            runConsole();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void runConsole() throws IOException {
        printMenu();

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String option = bufferRead.readLine();
            while (!option.equals("0")) {
                switch (option) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        updateBook();
                        break;
                    case "3":
                        deleteBook();
                        break;
                    case "4":
                        printAllBooks();
                        break;
                    case "5":
                        addClient();
                        break;
                    case "6":
                        updateClient();
                        break;
                    case "7":
                        deleteClient();
                        break;
                    case "8":
                        printAllClients();
                        break;
                    case "9":
                        addTransaction();
                        break;
                    case "10":
                        updateTransaction();
                        break;
                    case "11":
                        deleteTransaction();
                        break;
                    case "12":
                        printAllTransactions();
                        break;
                    case "13":
                        runFilter();
                        break;
                    case "14":
                        runReports();
                        break;
                    default:
                        System.out.println("This option is invalid.");
                }
                printMenu();
                option = bufferRead.readLine();
            }
            System.exit(0);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //FILTER OPERATIONS
    private void filterBooksByAuthor()  {
        Iterable<Book> books = bookService.getAllBooks();
        Set<Book> bookSet = (Set<Book>) books;

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String author = bufferRead.readLine();

            bookSet.removeIf(b -> !b.getAuthor().equals(author));
            bookSet.stream().forEach(System.out::println);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void filterClientsByAge() {
        Iterable<Client> clients = clientService.getAllClients();
        Set<Client> clientSet = (Set<Client>) clients;

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            int age = Integer.parseInt(bufferRead.readLine());

            clientSet.removeIf(c -> c.getAge() < age);
            clientSet.stream().forEach(System.out::println);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void filterTransactionsByDate() {
        Iterable<Transaction> transactions = transactionService.getAllTransactions();
        Set<Transaction> transactionSet = (Set<Transaction>) transactions;

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String date = bufferRead.readLine();

            transactionSet.removeIf(t -> !t.getOrderDate().equals(date));
            transactionSet.stream().forEach(System.out::println);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //REPORTS OPERATIONS
    private void sortBookDescendingByPrice() {
        Iterable<Book> books = bookService.getAllBooks();
        Set<Book> bookSet = (Set<Book>) books;

        bookSet.stream()
                .sorted(Comparator.comparingInt(Book::getPrice).reversed())
                .forEach(System.out::println);
    }

    private void sortClientAscendingByAge() {
        Iterable<Client> clients = clientService.getAllClients();
        Set<Client> clientSet = (Set<Client>) clients;

        clientSet.stream()
                .sorted(Comparator.comparingInt(Client::getAge))
                .forEach(System.out::println);
    }

    private void clientsWhoBoughtByAuthor2() {
        Iterable<Book> books = bookService.getAllBooks();
        Iterable<Client> clients = clientService.getAllClients();
        Iterable<Transaction> transactions = transactionService.getAllTransactions();
        Set<Transaction> transactionSet = (Set<Transaction>) transactions;
        Set<Book> bookSet = (Set<Book>) books;
        Set<Client> clientSet = (Set<Client>) clients;

        //Get id of books written by author 2
        bookSet.removeIf(b -> !b.getAuthor().equals("author2"));
        Set<Long> bookId = StreamSupport.stream(books.spliterator(), false)
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());

        //Get clients who bought these books using transaction class
        transactionSet.removeIf(t -> !bookId.contains(t.getId().getKey()));
        Set<Long> clientId = StreamSupport.stream(transactions.spliterator(), false)
                .map(BaseEntity::getId)
                .map(Pair::getValue)
                .collect(Collectors.toSet());

        //We check for the clients id
        clientSet.removeIf(c -> !clientId.contains(c.getId()));
        clientSet.stream()
                .forEach(System.out::println);
    }


    private void printAllBooks() {
        Iterable<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
    }

    private void printAllClients() {
        Iterable<Client> clients = clientService.getAllClients();
        clients.forEach(System.out::println);
    }

    private void printAllTransactions() {
        Iterable<Transaction> transactions = transactionService.getAllTransactions();
        transactions.forEach(System.out::println);
    }

    private void addBook() {
        try {
            Book book = readBook();
            bookService.addBook(book);
            System.out.println("Book was added successfully.");
        } catch (ValidatorException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addClient() {
        try {
            Client client = readClient();
            clientService.addClient(client);
            System.out.println("Client was added successfully.");
        } catch (ValidatorException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addTransaction() {
        try {
            Transaction transaction = readTransaction();
            transactionService.addTransaction(transaction);
            System.out.println("Transaction was added successfully.");
        } catch (ValidatorException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateBook() {
        try {
            Book book = readBook();
            bookService.updateBook(book);
            System.out.println("Book was updated successfully.");
        } catch (ValidatorException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateClient() {
        try {
            Client client = readClient();
            clientService.updateClient(client);
            System.out.println("Client was updated successfully.");
        } catch (ValidatorException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateTransaction() {
        try {
            Transaction transaction = readTransaction();
            transactionService.updateTransaction(transaction);
            System.out.println("Transaction was updated successfully.");
        } catch (ValidatorException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteBook() {
        System.out.println("Enter the id for the book: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            Long id = Long.valueOf(bufferRead.readLine());
            bookService.deleteBook(id);
            transactionService.deleteTransactionByBookId(id);

            System.out.println("Book was deleted successfully, with its associated transactions.");

        } catch (ValidatorException | IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParserConfigurationException | SQLException | TransformerException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void deleteClient() {
        System.out.println("Enter the id for the client: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            Long id = Long.valueOf(bufferRead.readLine());
            clientService.deleteClient(id);
            transactionService.deleteTransactionByClientId(id);

            System.out.println("Client was deleted successfully, with its associated transactions.");

        } catch (ValidatorException | IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParserConfigurationException | SQLException | TransformerException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void deleteTransaction() {
        System.out.println("Enter the id for the transaction: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long book_id = Long.valueOf(bufferRead.readLine());
            Long client_id = Long.valueOf(bufferRead.readLine());
            Pair<Long, Long> transaction_id = new Pair<>(book_id, client_id);
            transactionService.deleteTransaction(transaction_id);
            System.out.println("Transaction was deleted successfully.");
        } catch (ValidatorException |  IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParserConfigurationException | SQLException | TransformerException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Book readBook() throws IOException, NumberFormatException {
        System.out.println("Read book {id, title, author, price}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            //eventual exception: when id or price are given as string by user
            Long id = Long.valueOf(bufferRead.readLine());
            String title = bufferRead.readLine();
            String author = bufferRead.readLine();
            int price = Integer.parseInt(bufferRead.readLine());

            Book book = new Book(title, author, price);
            book.setId(id);

            return book;
        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private Client readClient() throws IOException, NumberFormatException {
        System.out.println("Read client {id, firstName, lastName, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            //eventual exception: when id or age are given as string by user
            Long id = Long.valueOf(bufferRead.readLine());
            String firstName = bufferRead.readLine();
            String lastName = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());

            Client client = new Client(firstName, lastName, age);
            client.setId(id);

            return client;
        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }

    }

    private Transaction readTransaction() throws IOException, NumberFormatException{
        System.out.println("Read transaction {book_id, client_id, transaction_number, transaction_code, transaction_date}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            //eventual exception: when book_id, client_id or transaction_number are given as string by user
            Long book_id = Long.valueOf(bufferRead.readLine());
            Long client_id = Long.valueOf(bufferRead.readLine());
            int transactionNumber = Integer.parseInt(bufferRead.readLine());
            String transactionCode = bufferRead.readLine();
            String transactionDate = bufferRead.readLine();

            Transaction transaction = new Transaction(transactionNumber, transactionCode, transactionDate);
            Pair<Long, Long> transaction_id = new Pair<>(book_id, client_id);
            transaction.setId(transaction_id);

            return transaction;
        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }
}