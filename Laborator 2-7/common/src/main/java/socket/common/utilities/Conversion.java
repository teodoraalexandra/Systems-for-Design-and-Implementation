package socket.common.utilities;

import javafx.scene.control.TableRow;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import socket.common.domain.Book;
import socket.common.domain.Client;
import socket.common.domain.Transaction;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.ref.Cleaner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Conversion {
    private String fileName;

    public Conversion(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void bookToXML(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        Node bookNode = bookToNode(book, document);
        root.appendChild(bookNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    public void clientToXML(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        Node clientNode = clientToNode(client, document);
        root.appendChild(clientNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    public void transactionToXML(Transaction transaction) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        Node transactionNode = transactionToNode(transaction, document);
        root.appendChild(transactionNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    private static Node bookToNode(Book book, Document document) {
        Element bookElement = document.createElement("book");
        String id = Long.toString(book.getId());
        bookElement.setAttribute("id", id);

        appendChildWithTextToNode(document, bookElement, "title", book.getTitle());
        appendChildWithTextToNode(document, bookElement, "author", book.getAuthor());
        appendChildWithTextToNode(document, bookElement, "price", String.valueOf(book.getPrice()));

        return bookElement;
    }

    private static Node clientToNode(Client client, Document document) {
        Element clientElement = document.createElement("client");
        String id = Long.toString(client.getId());
        clientElement.setAttribute("id", id);

        appendChildWithTextToNode(document, clientElement, "firstName", client.getFirstName());
        appendChildWithTextToNode(document, clientElement, "lastName", client.getLastName());
        appendChildWithTextToNode(document, clientElement, "age", String.valueOf(client.getAge()));

        return clientElement;
    }

    private static Node transactionToNode(Transaction transaction, Document document) {
        Element transactionElement = document.createElement("transaction");
        String id = Long.toString(transaction.getId().getKey()) + "-" + Long.toString(transaction.getId().getValue());
        transactionElement.setAttribute("id", id);

        appendChildWithTextToNode(document, transactionElement, "transactionNumber", String.valueOf(transaction.getTransactionNumber()));
        appendChildWithTextToNode(document, transactionElement, "transactionCode", transaction.getTransactionCode());
        appendChildWithTextToNode(document, transactionElement, "orderDate", transaction.getOrderDate());

        return transactionElement;
    }

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    public List<Book> xmlToBook() throws ParserConfigurationException, IOException, SAXException {
        List<Book> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createBookFromElement((Element) node))
                .collect(Collectors.toList());
    }

    public List<Client> xmlToClient() throws ParserConfigurationException, IOException, SAXException {
        List<Client> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createClientFromElement((Element) node))
                .collect(Collectors.toList());
    }

    public List<Transaction> xmlToTransaction() throws ParserConfigurationException, IOException, SAXException{
        List<Transaction> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createTransactionFromElement((Element) node))
                .collect(Collectors.toList());
    }

    private Book createBookFromElement(Element bookElement) {
        Book book = new Book();

        String id = bookElement.getAttribute("id");
        Long idLong = Long.parseLong(id);
        book.setId(idLong);

        book.setTitle(getTextFromTagName(bookElement, "title"));
        book.setAuthor(getTextFromTagName(bookElement, "author"));
        book.setPrice(Integer.parseInt(getTextFromTagName(bookElement, "price")));

        return book;
    }

    private Client createClientFromElement(Element clientElement) {
        Client client = new Client();

        String id = clientElement.getAttribute("id");
        Long idLong = Long.parseLong(id);
        client.setId(idLong);

        client.setFirstName(getTextFromTagName(clientElement, "firstName"));
        client.setLastName(getTextFromTagName(clientElement, "lastName"));
        client.setAge(Integer.parseInt(getTextFromTagName(clientElement, "age")));

        return client;
    }

    private Transaction createTransactionFromElement(Element transactionElement) {
        Transaction transaction = new Transaction();

        String id = transactionElement.getAttribute("id");
        String[] parts = id.split("-");
        String bookId = parts[0];
        String clientId = parts[1];

        Long idLongBook = Long.parseLong(bookId);
        Long idLongClient = Long.parseLong(clientId);
        Pair<Long, Long> pair = new Pair<>(idLongBook, idLongClient);
        transaction.setId(pair);

        transaction.setTransactionNumber(Integer.parseInt(getTextFromTagName(transactionElement, "transactionNumber")));
        transaction.setTransactionCode(getTextFromTagName(transactionElement, "transactionCode"));
        transaction.setOrderDate(getTextFromTagName(transactionElement, "orderDate"));

        return transaction;
    }


    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    public void deleteBookFromFile(Long idToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String idLong = Long.toString(idToDelete);
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        NodeList nodes = document.getElementsByTagName("book");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node nNode = nodes.item(i);

            String key = nNode.getAttributes().getNamedItem("id").getNodeValue();

            if (key.equals(idLong)) {
                root.removeChild(nNode);
            }
        }

       /*Stream.iterate(0, i -> i + 1)
                .limit (nodes.getLength())
                .map (nodes::item)
                .filter(node -> node.getAttributes().getNamedItem("id").getNodeValue().equals(idLong))
                .forEach(node -> root.removeChild(node));*/

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    public void deleteClientFromFile(Long idToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String idLong = Long.toString(idToDelete);
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        NodeList nodes = document.getElementsByTagName("client");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node nNode = nodes.item(i);

            String key = nNode.getAttributes().getNamedItem("id").getNodeValue();

            if (key.equals(idLong)) {
                root.removeChild(nNode);
            }
        }

       /*Stream.iterate(0, i -> i + 1)
                .limit (nodes.getLength())
                .map (nodes::item)
                .filter(node -> node.getAttributes().getNamedItem("id").getNodeValue().equals(idLong))
                .forEach(node -> root.removeChild(node));*/

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    public void deleteTransactionFromFile(Pair<Long, Long> pairId) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String idLong = Long.toString(pairId.getKey()) + "-" + Long.toString(pairId.getValue());
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        NodeList nodes = document.getElementsByTagName("transaction");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node nNode = nodes.item(i);

            String key = nNode.getAttributes().getNamedItem("id").getNodeValue();

            if (key.equals(idLong)) {
                root.removeChild(nNode);
            }
        }

        /*Stream.iterate(0, i -> i + 1)
                .limit (nodes.getLength())
                .map (nodes::item)
                .filter(node -> node.getAttributes().getNamedItem("id").getNodeValue().equals(idLong))
                .forEach(node -> root.removeChild(node));*/

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    private static final String CSV_SEPARATOR = ",";
    public void writeBookToCSV(ArrayList<Book> bookList)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName)));
            for (Book book : bookList)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(book.getId() <=0 ? "" : book.getId());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(book.getTitle().trim().length() == 0? "" : book.getTitle());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(book.getAuthor().trim().length() == 0 ? "" : book.getAuthor());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(book.getPrice() <= 0 ? "" : book.getPrice());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ignored){}
    }

    public void writeClientToCSV(ArrayList<Client> clientList)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName)));
            for (Client client : clientList)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(client.getId() <=0 ? "" : client.getId());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(client.getFirstName().trim().length() == 0? "" : client.getFirstName());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(client.getLastName().trim().length() == 0 ? "" : client.getLastName());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(client.getAge() <= 0 ? "" : client.getAge());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ignored){}
    }

    public void writeTransactionToCSV(ArrayList<Transaction> transactionList)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName)));
            for (Transaction transaction : transactionList)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(transaction.getId().getKey() <=0 ? "" : transaction.getId().getKey());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(transaction.getId().getValue() <=0 ? "" : transaction.getId().getValue());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(transaction.getTransactionNumber() <= 0? "" : transaction.getTransactionNumber());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(transaction.getTransactionCode().trim().length() == 0 ? "" : transaction.getTransactionCode());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(transaction.getOrderDate().trim().length() == 0 ? "" : transaction.getOrderDate());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ignored){}
    }

    public List<Book> readBooksFromCSV() {
        String fileName = this.fileName;
        List<Book> books = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");
                Book book = createBook(attributes);
                books.add(book);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Client> readClientFromCSV() {
        String fileName = this.fileName;
        List<Client> clients = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");
                Client client = createClient(attributes);
                clients.add(client);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public List<Transaction> readTransactionFromCSV() {
        String fileName = this.fileName;
        List<Transaction> transactions = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");
                Transaction transaction = createTransaction(attributes);
                transactions.add(transaction);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private static Book createBook(String[] data) {
        Long id = Long.parseLong(data[0]);
        String title = data[1];
        String author = data[2];
        int price = Integer.parseInt(data[3]);

        Book book = new Book(title, author, price);
        book.setId(id);
        return book;
    }

    private static Client createClient(String[] data) {
        Long id = Long.parseLong(data[0]);
        String firstName = data[1];
        String lastName = data[2];
        int age = Integer.parseInt(data[3]);

        Client client = new Client(firstName, lastName, age);
        client.setId(id);
        return client;
    }

    private static Transaction createTransaction(String[] data) {
        Long book_id = Long.parseLong(data[0]);
        Long client_id = Long.parseLong(data[1]);
        int transactionNumber = Integer.parseInt(data[2]);
        String transactionCode = data[3];
        String orderDate = data[4];
        Pair<Long, Long> id = new Pair<>(book_id, client_id);

        Transaction transaction = new Transaction(transactionNumber, transactionCode, orderDate);
        transaction.setId(id);
        return transaction;
    }
}
