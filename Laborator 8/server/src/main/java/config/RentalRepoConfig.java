package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import repository.RentalRepositoryImpl;
import repository.*;

@Configuration
public class RentalRepoConfig {
    @Bean
    RentalRepo RentalRepo() { return new RentalRepositoryImpl(); }
}