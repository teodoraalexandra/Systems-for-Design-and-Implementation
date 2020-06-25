package socket.server;

import org.xml.sax.SAXException;
import socket.common.BookService;
import socket.common.ClientService;
import socket.common.Message;
import socket.common.TransactionService;
import socket.common.domain.Book;
import socket.server.service.BookServiceImpl;
import socket.server.service.ClientServiceImpl;
import socket.server.service.TransactionServiceImpl;
import socket.server.tcp.TcpServer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {

    public static void main(String[] args) {
        try {
            System.out.println("Server started");

            TcpServer tcpServer = new TcpServer();
            tcpServer.startServer();

        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
        }

    }

}
