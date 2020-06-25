package socket.client;

import socket.client.service.BookServiceClient;
import socket.client.service.ClientServiceClient;
import socket.client.service.TransactionServiceClient;
import socket.common.BookService;
import socket.common.ClientService;
import socket.common.TransactionService;
import socket.client.tcp.TcpClient ;
import socket.client.ui.Console ;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClientApp {

    public static void main(String[] args) throws IOException {
            ExecutorService executorService = Executors.newFixedThreadPool(4);

            TcpClient tcpClient = new TcpClient();

            BookService bookService = new BookServiceClient(executorService, tcpClient);
            ClientService clientService = new ClientServiceClient(executorService, tcpClient);
            TransactionService transactionService = new TransactionServiceClient(executorService, tcpClient);
            Console console = new Console(bookService, clientService, transactionService);

            console.runConsole();

            executorService.shutdown();

            System.out.println("Bye client");

    }
}

