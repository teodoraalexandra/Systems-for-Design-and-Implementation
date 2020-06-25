package repository_2.repository.xml;

import repository_2.domain.Book;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import repository_2.repository.inmemory.InMemoryRepository;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BookXMLRepository extends InMemoryRepository<Long, Book> {
    private String fileName;
    private List<Book> books;

    public BookXMLRepository(String fileName) throws IOException, SAXException, ParserConfigurationException {
        this.fileName = fileName;
        List<Book> books = loadData();
    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    private Book createBookFromElement(Element bookElement) {
        Book book = new Book();

        String id = bookElement.getAttribute("id");
        Long idLong = Long.parseLong(id);
        book.setId(idLong);

        book.setTitle(getTextFromTagName(bookElement, "title"));
        book.setAuthor(getTextFromTagName(bookElement, "author"));
        book.setPrice(Integer.parseInt(getTextFromTagName(bookElement, "price")));

        try {
            super.save(book);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    private List<Book> loadData() throws ParserConfigurationException, IOException, SAXException {
        List<Book> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/books.xml");

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createBookFromElement((Element) node))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> save(Book entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/books.xml");

        Element root = document.getDocumentElement();
        Node bookNode = bookToNode(book, document);
        root.appendChild(bookNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("./data/books.xml")));
    }

    @Override
    public Optional<Book> delete(Long id) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        deleteFromFile(id);
        return super.delete(id);
    }

    private void deleteFromFile(Long idToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String idLong = Long.toString(idToDelete);
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/books.xml");

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
                new StreamResult(new File("./data/books.xml")));
    }

    @Override
    public Optional<Book> update(Book entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        updateToFile(entity);
        return super.update(entity);
    }

    private void updateToFile(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Long id = book.getId();
        String idString = Long.toString(id);

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/books.xml");

        Element root = document.getDocumentElement();
        NodeList nodes = document.getElementsByTagName("book");
        Node newNode = bookToNode(book, document);

        Stream.iterate(0, i -> i + 1)
                .limit (nodes.getLength())
                .map (nodes::item)
                .filter(node -> node.getAttributes().getNamedItem("id").getNodeValue().equals(idString))
                .forEach(node -> root.replaceChild(newNode, node));

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("./data/books.xml")));
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

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }
}
