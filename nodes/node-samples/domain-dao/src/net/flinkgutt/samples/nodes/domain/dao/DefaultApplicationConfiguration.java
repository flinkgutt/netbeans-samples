package net.flinkgutt.samples.nodes.domain.dao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Christian
 */
@Configuration
@EnableTransactionManagement
public class DefaultApplicationConfiguration {
    
    /*
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/netbeans-samples");
        dataSource.setUsername("netbeans-samples");
        dataSource.setPassword("secret123");
        return dataSource;
    }*/

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        //CustomerRoutingDataSource dataSource = new CustomerRoutingDataSource();
        RoutingDataSource dataSource = new RoutingDataSource();
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        org.springframework.jdbc.datasource.DataSourceTransactionManager transactionManager = new org.springframework.jdbc.datasource.DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
