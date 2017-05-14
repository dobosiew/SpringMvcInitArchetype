package ${package}.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"${package}.domain"},
        repositoryImplementationPostfix = "Impl")
@PropertySource("classpath:database.properties")
public class DataBaseConfiguration {

    @Resource
    public Environment environment;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.db.driver"));
        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.db.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.db.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.db.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(getDataSource());
        entityManagerFactory.setPackagesToScan(
                environment.getRequiredProperty("entitymanager.packagesToScan"));

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        Properties additionalProperties = new Properties();
        additionalProperties.put(
                "hibernate.dialect", environment.getProperty("hibernate.dialect"));
        additionalProperties.put(
                "hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        additionalProperties.put(
                "hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        additionalProperties.put(
                "hibernate.ejb.naming_strategy", environment.getProperty("hibernate.ejb.naming_strategy"));
        additionalProperties.put(
                "hibernate.hbm2ddl.import_files", environment.getProperty("hibernate.hbm2ddl.import_files"));

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
