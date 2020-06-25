package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import repository.ClientRepositoryImpl;
import repository.*;

@Configuration
public class ClientRepoConfig {
    @Bean
    ClientRepo ClientRepo(){
        return new ClientRepositoryImpl();
    }
}