package socket.client.service;

import socket.client.tcp.TcpClient;
import socket.common.BookService;
import socket.common.Message;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class BookServiceClient implements BookService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public BookServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public String addBook(String fileName) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(BookService.addBook, fileName);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String updateBook(String fileName) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(BookService.updateBook, fileName);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String deleteBook(String id) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(BookService.deleteBook, id);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String printBooks() {
        //create a request
        //send request to server
        //get response

        Message request = new Message(BookService.printBooks);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }
}
