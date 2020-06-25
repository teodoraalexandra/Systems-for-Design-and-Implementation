package socket.client.service;

import socket.client.tcp.TcpClient;
import socket.common.ClientService;
import socket.common.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientServiceClient implements ClientService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ClientServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public String addClient(String fileName) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(ClientService.addClient, fileName);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String updateClient(String fileName) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(ClientService.updateClient, fileName);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String deleteClient(String id) {
        //create a request
        //send request to server
        //get response

        Message request = new Message(ClientService.deleteClient, id);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }

    @Override
    public String printClients() {
        //create a request
        //send request to server
        //get response

        Message request = new Message(ClientService.printClients);
        System.out.println("Sending request: " + request);
        Message response = tcpClient.sendAndReceive(request);
        System.out.println("Received response: " + response);

        return response.getBody();
    }
}

