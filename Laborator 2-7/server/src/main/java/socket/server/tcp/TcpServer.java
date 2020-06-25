package socket.server.tcp;

import org.xml.sax.SAXException;
import socket.common.*;
import socket.common.domain.Client;
import socket.server.service.BookServiceImpl;
import socket.server.service.ClientServiceImpl;
import socket.server.service.TransactionServiceImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.UnaryOperator;

public class TcpServer {
    public static final String OK = "ok";
    public static final String ERROR = "error";
    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    private BookService bookService;
    private ClientService clientService;
    private TransactionService transactionService;

    public TcpServer() {
        this.methodHandlers = new HashMap<>();
        this.executorService = Executors.newFixedThreadPool(4);
        this.bookService = new BookServiceImpl(this.executorService);
        this.clientService = new ClientServiceImpl(this.executorService);
        this.transactionService = new TransactionServiceImpl(this.executorService);
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() throws IOException {
        ServerSocket listener = new ServerSocket(Message.PORT);
        while (true) {
            Socket client = listener.accept();
            ClientHandler clientThread = new ClientHandler(client);
            clients.add(clientThread);
            executorService.execute(clientThread);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            try {
                //while (true) {
                    var is = socket.getInputStream();
                    var os = socket.getOutputStream();

                    Message request = new Message();
                    request.readFrom(is);
                    System.out.println("Received request: " + request + " from " + this.socket);

                    if (request.getHeader().equals("printing books")) {
                        String result = bookService.printBooks();
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("printing clients")) {
                        String result = clientService.printClients();
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("printing transactions")) {
                        String result = transactionService.printTransactions();
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("adding book")) {
                        String fileName = request.getBody();
                        String result = bookService.addBook(fileName);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("adding client")) {
                        String fileName = request.getBody();
                        String result = clientService.addClient(fileName);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("adding transaction")) {
                        String fileName = request.getBody();
                        String result = transactionService.addTransaction(fileName);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("updating book")) {
                        String fileName = request.getBody();
                        String result = bookService.updateBook(fileName);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("updating client")) {
                        String fileName = request.getBody();
                        String result = clientService.updateClient(fileName);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("updating transaction")) {
                        String fileName = request.getBody();
                        String result = transactionService.updateTransaction(fileName);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("deleting book")) {
                        String id = request.getBody();
                        String result = bookService.deleteBook(id);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("deleting client")) {
                        String id = request.getBody();
                        String result = clientService.deleteClient(id);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }

                    if (request.getHeader().equals("deleting transaction")) {
                        String id = request.getBody();
                        String result = transactionService.deleteTransaction(id);
                        Message response = new Message("ok", result);
                        response.writeTo(os);

                        System.out.println("Response send: " + response);
                    }
                //}
                } catch (IOException e) {
                    throw new BookServiceException("Error processing client", e);
                } catch (ParserConfigurationException | TransformerException | SAXException | SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}

