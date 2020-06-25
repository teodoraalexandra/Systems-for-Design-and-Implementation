package repository_2.repository.xml;

import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repository_2.domain.Transaction;
import repository_2.repository.inmemory.InMemoryRepository;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TransactionXMLRepository extends InMemoryRepository<Pair<Long, Long>, Transaction> {
    String fileName;
    private List<Transaction> transactions;

    public TransactionXMLRepository(String fileName) throws IOException, SAXException, ParserConfigurationException {
        this.fileName = fileName;
        List<Transaction> transactions = loadData();
    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
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

        try {
            super.save(transaction);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    private List<Transaction> loadData() throws ParserConfigurationException, IOException, SAXException {
        List<Transaction> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/transactions.xml");

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createTransactionFromElement((Element) node))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<Transaction> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Transaction transaction) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/transactions.xml");

        Element root = document.getDocumentElement();
        Node transactionNode = transactionToNode(transaction, document);
        root.appendChild(transactionNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("./data/transactions.xml")));
    }

    @Override
    public Optional<Transaction> delete(Pair<Long, Long> pairId) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        deleteFromFile(pairId);
        return super.delete(pairId);
    }

    private void deleteFromFile(Pair<Long, Long> pairId) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String idLong = Long.toString(pairId.getKey()) + "-" + Long.toString(pairId.getValue());
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/transactions.xml");

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
                new StreamResult(new File("./data/transactions.xml")));
    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        updateToFile(entity);
        return super.update(entity);
    }

    private void updateToFile(Transaction transaction) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Pair<Long, Long> id = transaction.getId();
        String idString = Long.toString(id.getKey()) + "-" + Long.toString(id.getValue());

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/transactions.xml");

        Element root = document.getDocumentElement();
        NodeList nodes = document.getElementsByTagName("transaction");
        Node newNode = transactionToNode(transaction, document);

        Stream.iterate(0, i -> i + 1)
                .limit (nodes.getLength())
                .map (nodes::item)
                .filter(node -> node.getAttributes().getNamedItem("id").getNodeValue().equals(idString))
                .forEach(node -> root.replaceChild(newNode, node));

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("./data/transactions.xml")));
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
}
