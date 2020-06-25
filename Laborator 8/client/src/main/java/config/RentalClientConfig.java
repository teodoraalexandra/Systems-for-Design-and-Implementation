package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import domain.*;
import service.*;

@Configuration
public class RentalClientConfig {
    @Bean
    RentalServiceClient RentalServiceClient() { return new RentalServiceClient(); }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanRental() {
        RmiProxyFactoryBean proxy = new RmiProxyFactoryBean();
        proxy.setServiceInterface(RentalService.class);
        proxy.setServiceUrl("rmi://localhost:1099/RentalService");
        return proxy;
    }
}