package socket.common;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Future;

public interface ClientService {
    String addClient = "adding client";
    String updateClient = "updating client";
    String deleteClient = "deleting client";
    String printClients = "printing clients";

    String addClient(String fileName) throws ParserConfigurationException, IOException, SAXException, TransformerException;
    String updateClient(String fileName) throws ParserConfigurationException, TransformerException, SAXException, IOException;
    String deleteClient(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException;
    String printClients() throws IOException;
}
