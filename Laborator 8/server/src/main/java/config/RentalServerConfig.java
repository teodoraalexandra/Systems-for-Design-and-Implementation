package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import domain.*;
import controller.RentalServiceServer;

@Configuration
public class RentalServerConfig {
    @Bean
    RentalService RentalService() {
        return (domain.RentalService) new RentalServiceServer();
    }

    @Bean
    RmiServiceExporter rmiServiceExporterRental() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("RentalService");
        exporter.setServiceInterface(RentalService.class);
        exporter.setService(RentalService());
        return exporter;
    }
}