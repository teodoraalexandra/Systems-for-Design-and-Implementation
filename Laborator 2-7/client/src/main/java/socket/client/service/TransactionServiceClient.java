package socket.client.service;

import org.xml.sax.SAXException;
import socket.client.tcp.TcpClient;
import socket.common.BookService;
import socket.common.ClientService;
import socket.common.TransactionService;
import socket.common.Message;
import socket.common.domain.Book;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TransactionServiceClient implements TransactionService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public TransactionServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public String addTransaction(String fileName) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(TransactionService.addTransaction, fileName);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String updateTransaction(String fileName) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(TransactionService.updateTransaction, fileName);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String deleteTransaction(String id) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(TransactionService.deleteTransaction, id);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String deleteTransactionByBookId(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        //create a request
        //send request to server
        //get response

        Message request = new Message(TransactionService.deleteTransactionByBookId, id);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String deleteTransactionByClientId(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        //create a request
        //send request to server
        //get response

        Message request = new Message(TransactionService.deleteTransactionByClientId, id);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String printTransactions() {
        //create a request
        //send request to server
        //get response

        Message request = new Message(TransactionService.printTransactions);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }
}

