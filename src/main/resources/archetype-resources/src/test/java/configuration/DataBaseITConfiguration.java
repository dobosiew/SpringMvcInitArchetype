package ${package}.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Profile("integration-test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"${package}.domain"})
@PropertySource(value = "classpath:databaseIT.properties")
public class DataBaseITConfiguration {

    @Value("${spring.datasource.db.driver}")
    private String dbDriver;
    @Value("${spring.datasource.db.url}")
    private String dbUrl;
    @Value("${spring.datasource.db.username}")
    private String dbUsername;
    @Value("${spring.datasource.db.password}")
    private String dbPassword;

    @Value("${entitymanager.packagesToScan}")
    private String emPackageToScan;

    @Value("${hibernate.dialect}")
    private String hibarnateDialect;
    @Value("${hibernate.show_sql}")
    private String hibarnateShowSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibarnateHbm2ddlAuto;
    @Value("${hibernate.ejb.naming_strategy}")
    private String hibarnateNamingStrategy;
    @Value("${hibernate.hbm2ddl.import_files}")
    private String hibarnateImportFiles;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(getDataSource());
        entityManagerFactory.setPackagesToScan(emPackageToScan);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect", hibarnateDialect);
        additionalProperties.put("hibernate.show_sql", hibarnateShowSql);
        additionalProperties.put("hibernate.hbm2ddl.auto", hibarnateHbm2ddlAuto);
        additionalProperties.put("hibernate.ejb.naming_strategy", hibarnateNamingStrategy);
        additionalProperties.put("hibernate.hbm2ddl.import_files", hibarnateImportFiles);

        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
