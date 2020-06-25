package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import domain.*;
import controller.ClientServiceServer;

@Configuration
public class ClientServerConfig {
    @Bean
    ClientService ClientService() {
        return (domain.ClientService) new ClientServiceServer();
    }

    @Bean
    RmiServiceExporter rmiServiceExporterClient() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("ClientService");
        exporter.setServiceInterface(ClientService.class);
        exporter.setService(ClientService());
        return exporter;
    }
}