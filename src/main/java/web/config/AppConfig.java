package web.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import web.exception.ConfigException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(value = "web")
public class AppConfig {

   private final Environment env;

   @Autowired
   public AppConfig(Environment env) {
      this.env = env;
   }

   @Bean
   public DataSource getDataSource() {
      try{
         DriverManagerDataSource dataSource = new DriverManagerDataSource();
         dataSource.setDriverClassName(env.getProperty("db.driver"));
         dataSource.setUrl(env.getProperty("db.url"));
         dataSource.setUsername(env.getProperty("db.username"));
         dataSource.setPassword(env.getProperty("db.password"));
         return dataSource;
      }catch (Exception e){
         throw new ConfigException("Failed to get DataSource");
      }
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      try{
         LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
         em.setDataSource(getDataSource());
         em.setPackagesToScan("web.model");
         HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
         em.setJpaVendorAdapter(vendorAdapter);
         em.setJpaProperties(hibernateProperties());
         return em;
      }catch (Exception e){
         throw new ConfigException("Failed to get LocalContainerEntityManagerFactoryBean");
      }
   }

   @Bean
   public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
      try{
         JpaTransactionManager transactionManager = new JpaTransactionManager();
         transactionManager.setEntityManagerFactory(entityManagerFactory);
         return transactionManager;
      }catch (Exception e){
         throw new ConfigException("Failed to get JpaTransactionManager");
      }
   }

   @Bean
   public ModelMapper modelMapper() {
      try{
         ModelMapper modelMapper = new ModelMapper();
         modelMapper.getConfiguration()
                 .setMatchingStrategy(MatchingStrategies.STRICT)
                 .setSkipNullEnabled(true);
         return modelMapper;
      }catch (Exception e){
         throw new ConfigException("Failed to get ModelMapper");
      }
   }

   @Bean
   public Properties hibernateProperties() {
      try{
         Properties properties = new Properties();
         properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
         properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
         properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
         if(properties.size() < 3) {
            throw new ConfigException();
         }
         return properties;
      }catch (ConfigException e){
         throw new ConfigException("No data needed in hibernateProperties");
      }
   }
}
