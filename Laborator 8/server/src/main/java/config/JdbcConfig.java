package config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class JdbcConfig {

    @Bean
    JdbcOperations jdbcOperations() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    DataSource dataSource() {
        System.out.println("Server ---> connected to database!");
        BasicDataSource ds = new BasicDataSource();
        //Camelia
        //ds.setDriverClassName("com.mysql.jdbc.Driver");

        //Camelia
        //ds.setUrl("jdbc:mysql://localhost:3306/mpp");

        //Teodora
        ds.setUrl("jdbc:postgresql://localhost:5432/mpp");

        //Alina
        //ds.setUrl("jdbc:postgresql://localhost:3306/mpp");

        ds.setUsername("root");
        ds.setPassword("");
        ds.setInitialSize(2);
        return ds;
    }
}