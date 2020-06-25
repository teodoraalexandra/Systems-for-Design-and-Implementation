package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import domain.*;
import controller.MovieServiceServer;

@Configuration
public class MovieServerConfig {
    @Bean
    MovieService MovieService() {
        return (domain.MovieService) new MovieServiceServer();
    }

    @Bean
    RmiServiceExporter rmiServiceExporterMovie() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("MovieService");
        exporter.setServiceInterface(MovieService.class);
        exporter.setService(MovieService());
        return exporter;
    }
}