package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import repository.MovieRepositoryImpl;
import repository.*;

@Configuration
public class MovieRepoConfig {
    @Bean
    MovieRepo MovieRepo() {
        return new MovieRepositoryImpl();
    }
}