package lab9.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"lab9.repository", "lab9.service", "lab9.ui"})
public class CatalogConfig {

}
