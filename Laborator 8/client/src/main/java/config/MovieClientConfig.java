package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import domain.*;
import service.*;

@Configuration
public class MovieClientConfig {
    @Bean
    MovieServiceClient problemServiceClient() {
        return new MovieServiceClient();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanMovie() {
        RmiProxyFactoryBean proxy = new RmiProxyFactoryBean();
        proxy.setServiceInterface(MovieService.class);
        proxy.setServiceUrl("rmi://localhost:1099/MovieService");
        return proxy;
    }
}