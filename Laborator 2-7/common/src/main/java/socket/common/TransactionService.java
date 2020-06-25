package socket.common;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Future;

public interface TransactionService {
    String addTransaction = "adding transaction";
    String updateTransaction = "updating transaction";
    String deleteTransaction = "deleting transaction";
    String deleteTransactionByBookId = "deleting transaction by book id";
    String deleteTransactionByClientId = "deleting transaction by client id";
    String printTransactions = "printing transactions";

    String addTransaction(String fileName) throws ParserConfigurationException, IOException, SAXException, TransformerException;
    String updateTransaction(String fileName) throws ParserConfigurationException, TransformerException, SAXException, IOException;
    String deleteTransaction(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException;
    String deleteTransactionByBookId(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException;
    String deleteTransactionByClientId(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException;
    String printTransactions() throws IOException;
}