package socket.client.tcp;

import socket.common.BookServiceException;
import socket.common.Message;

import java.io.IOException;
import java.net.Socket;


public class TcpClient{

    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(Message.HOST, Message.PORT);
             var is = socket.getInputStream();
             var os = socket.getOutputStream()
        ) {
            //System.out.println("sendAndReceive - sending request: " + request);
            request.writeTo(os);

            //System.out.println("sendAndReceive - received response: ");
            Message response = new Message();
            response.readFrom(is);

            return response;
        } catch (IOException e) {
            throw new BookServiceException("Error connection to server " + e.getMessage(), e);
        }
    }
}

