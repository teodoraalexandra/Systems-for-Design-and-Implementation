package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import domain.*;
import service.*;

@Configuration
public class ClientClientConfig {
    @Bean
    ClientServiceClient ClientServiceClient() {
        return new ClientServiceClient();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanClient() {
        RmiProxyFactoryBean proxy = new RmiProxyFactoryBean();
        proxy.setServiceInterface(ClientService.class);
        proxy.setServiceUrl("rmi://localhost:1099/ClientService");
        return proxy;
    }
}