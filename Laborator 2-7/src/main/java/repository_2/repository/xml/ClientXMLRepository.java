package repository_2.repository.xml;

import repository_2.domain.Client;

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

public class ClientXMLRepository extends InMemoryRepository<Long, Client> {
    String fileName;

    public ClientXMLRepository(String fileName) throws IOException, SAXException, ParserConfigurationException {
        this.fileName = fileName;
        List<Client> clients = loadData();
    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    private Client createClientFromElement(Element clientElement) {
        Client client = new Client();

        String id = clientElement.getAttribute("id");
        Long idLong = Long.parseLong(id);
        client.setId(idLong);

        client.setFirstName(getTextFromTagName(clientElement, "firstName"));
        client.setLastName(getTextFromTagName(clientElement, "lastName"));
        client.setAge(Integer.parseInt(getTextFromTagName(clientElement, "age")));

        try {
            super.save(client);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    private List<Client> loadData() throws ParserConfigurationException, IOException, SAXException {
        List<Client> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/clients.xml");

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createClientFromElement((Element) node))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> save(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/clients.xml");

        Element root = document.getDocumentElement();
        Node clientNode = clientToNode(client, document);
        root.appendChild(clientNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("./data/clients.xml")));
    }

    @Override
    public Optional<Client> delete(Long id) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        deleteFromFile(id);
        return super.delete(id);
    }

    private void deleteFromFile(Long idToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String idLong = Long.toString(idToDelete);
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/clients.xml");

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
                new StreamResult(new File("./data/clients.xml")));
    }

    @Override
    public Optional<Client> update(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        updateToFile(entity);
        return super.update(entity);
    }

    private void updateToFile(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Long id = client.getId();
        String idString = Long.toString(id);

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/clients.xml");

        Element root = document.getDocumentElement();
        NodeList nodes = document.getElementsByTagName("client");
        Node newNode = clientToNode(client, document);

        Stream.iterate(0, i -> i + 1)
                .limit (nodes.getLength())
                .map (nodes::item)
                .filter(node -> node.getAttributes().getNamedItem("id").getNodeValue().equals(idString))
                .forEach(node -> root.replaceChild(newNode, node));

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("./data/clients.xml")));
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

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }
}
