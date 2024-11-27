package fusionIQ.AI.V2.fusionIq.service;

import com.zaxxer.hikari.HikariConfig;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://fusionv2test.cluster-c7q0igskqolv.ap-south-1.rds.amazonaws.com:3306/prod?createDatabaseIfNotExist=true");
        config.setUsername("surnoi");
        config.setPassword("Surnoi11240");
        config.addDataSourceProperty("connectionInitSql", "SET GLOBAL max_allowed_packet=134217728"); // 128 MB
        return new HikariDataSource(config);
    }
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}

