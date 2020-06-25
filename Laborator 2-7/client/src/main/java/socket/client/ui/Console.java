package socket.client.ui;

import javafx.util.Pair;
import org.xml.sax.SAXException;
import socket.common.BookService;
import socket.common.ClientService;
import socket.common.TransactionService;
import socket.common.domain.Book;
import socket.common.domain.Client;
import socket.common.domain.Transaction;
import socket.common.utilities.Conversion;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

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

    //OPERATIONS FOR THE TRANSACTION
    private void printAllTransactions() throws IOException {
        AtomicReference<String> responseFromServer = new AtomicReference<>("");
        //Here: the response from server must be the file where the server write all transactions

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(transactionService.printTransactions());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { //System.out.println("Server response: " + responseFromServer);
            Conversion conversion = new Conversion(responseFromServer.get());
            List<Transaction> transactions = conversion.readTransactionFromCSV();

            for (Transaction t: transactions) {
                System.out.println(t);
            } });
    }

    private void addTransaction() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        Transaction transaction = readTransaction();

        //Convert transaction into xml
        String fileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/transactionSerialize.xml";
        Conversion conversion = new Conversion(fileName);
        conversion.transactionToXML(transaction);

        AtomicReference<String> responseFromServer = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(transactionService.addTransaction(fileName));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer.get()); });
    }

    private void updateTransaction() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        Transaction transaction = readTransaction();

        //Convert transaction into xml
        String fileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/transactionSerialize.xml";
        Conversion conversion = new Conversion(fileName);
        conversion.transactionToXML(transaction);

        AtomicReference<String> responseFromServer = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(transactionService.updateTransaction(fileName));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer.get()); });
    }

    private void deleteTransaction() throws IOException, SQLException, ParserConfigurationException, SAXException, TransformerException {
        System.out.println("Enter the id for the transaction: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long book_id = Long.valueOf(bufferRead.readLine());
        Long client_id = Long.valueOf(bufferRead.readLine());

        String idBookConverted = Long.toString(book_id);
        String idClientConverted = Long.toString(client_id);
        String transaction_id = idBookConverted + "-" + idClientConverted;

        AtomicReference<String> responseFromServer = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(transactionService.deleteTransaction(transaction_id));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException | SQLException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer.get()); });
    }

    private Transaction readTransaction() throws IOException, NumberFormatException {
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


    //OPERATIONS FOR THE CLIENT
    private void printAllClients() throws IOException {
        AtomicReference<String> responseFromServer = new AtomicReference<>("");
        //Here: the response from server must be the file where the server write all clients

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(clientService.printClients());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { //System.out.println("Server response: " + responseFromServer);
            Conversion conversion = new Conversion(responseFromServer.get());
            List<Client> clients = conversion.readClientFromCSV();

            for (Client c: clients) {
                System.out.println(c);
            } });
    }

    private void addClient() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        Client client = readClient();

        //Convert client into xml
        String fileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/clientSerialize.xml";
        Conversion conversion = new Conversion(fileName);
        conversion.clientToXML(client);

        AtomicReference<String> responseFromServer = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(clientService.addClient(fileName));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer.get()); });
    }

    private void updateClient() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        Client client = readClient();

        //Convert client into xml
        String fileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/clientSerialize.xml";
        Conversion conversion = new Conversion(fileName);
        conversion.clientToXML(client);

        AtomicReference<String> responseFromServer = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(clientService.updateClient(fileName));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer.get()); });
    }

    private void deleteClient() throws IOException, SQLException, ParserConfigurationException, SAXException, TransformerException {
        System.out.println("Enter the id for the client: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = Long.valueOf(bufferRead.readLine());
        String idConverted = Long.toString(id);

        AtomicReference<String> responseFromServer_1 = new AtomicReference<>("");
        AtomicReference<String> responseFromServer_2 = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer_1.set(clientService.deleteClient(idConverted));
                responseFromServer_2.set(transactionService.deleteTransactionByClientId(idConverted));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException | SQLException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer_1.get());
            System.out.println(responseFromServer_2.get());});
    }

    private Client readClient() throws IOException, NumberFormatException {
        System.out.println("Read client {id, firstName, lastName, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
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

    //OPERATIONS FOR THE BOOK
    private void printAllBooks() throws IOException {
        AtomicReference<String> responseFromServer = new AtomicReference<>("");
        //Here: the response from server must be the file where the server write all books

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(bookService.printBooks());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { //System.out.println("Server response: " + responseFromServer);
            Conversion conversion = new Conversion(responseFromServer.get());
            List<Book> books = conversion.readBooksFromCSV();

            for (Book b: books) {
                System.out.println(b);
            } });
    }

    private void addBook() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        Book book = readBook();

        //Convert book into xml
        String fileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/bookSerialize.xml";
        Conversion conversion = new Conversion(fileName);
        conversion.bookToXML(book);
        AtomicReference<String> responseFromServer = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(bookService.addBook(fileName));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                        e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer.get()); });
    }

    private void updateBook() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        Book book = readBook();

        //Convert book into xml
        String fileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/bookSerialize.xml";
        Conversion conversion = new Conversion(fileName);
        conversion.bookToXML(book);
        AtomicReference<String> responseFromServer = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer.set(bookService.updateBook(fileName));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer.get()); });
    }

    private void deleteBook() throws IOException, SQLException, ParserConfigurationException, SAXException, TransformerException {
        System.out.println("Enter the id for the book: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = Long.valueOf(bufferRead.readLine());
        String idConverted = Long.toString(id);

        AtomicReference<String> responseFromServer_1 = new AtomicReference<>("");
        AtomicReference<String> responseFromServer_2 = new AtomicReference<>("");

        CompletableFuture sync = CompletableFuture.runAsync(() -> {
            try {
                responseFromServer_1.set(bookService.deleteBook(idConverted));
                responseFromServer_2.set(transactionService.deleteTransactionByBookId(idConverted));
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException | SQLException e) {
                e.printStackTrace();
            }
        }).thenAccept( e -> { System.out.println(responseFromServer_1.get());
            System.out.println(responseFromServer_2.get());});
    }

    private Book readBook() throws IOException, NumberFormatException {
        System.out.println("Read book {id, title, author, price}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
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

    public void runConsole() throws IOException {
        while (true) {
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
            } catch (IOException | TransformerException | ParserConfigurationException | SAXException | SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
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

    //todo: include filters and reports here
    private void filterTransactionsByDate() {
    }

    private void filterClientsByAge() {
    }

    private void filterBooksByAuthor() {
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

    private void clientsWhoBoughtByAuthor2() {
    }

    private void sortClientAscendingByAge() {
    }

    private void sortBookDescendingByPrice() {
    }

}

