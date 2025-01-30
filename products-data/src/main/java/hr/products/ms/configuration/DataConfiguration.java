package hr.products.ms.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for data beans.
 */
@Configuration
@EntityScan(basePackages = "hr.products.ms.entity")
@EnableJpaRepositories(basePackages = "hr.products.ms.repository")
@EnableTransactionManagement
public class DataConfiguration {

}
