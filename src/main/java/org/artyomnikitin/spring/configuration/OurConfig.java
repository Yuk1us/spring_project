package org.artyomnikitin.spring.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

import java.beans.PropertyVetoException;
import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.artyomnikitin.spring")
@EnableWebMvc
@EnableTransactionManagement
public class OurConfig {


    private String[] parameter = new String[10];


    /**Configure PostgreSQL Connection*/
    @Bean
    public DataSource dataSource(){
        String string = Paths.get("").toAbsolutePath().getParent().getParent().toString();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(string+"/.env"))) {
            for (int i = 0; bufferedReader.ready(); i++) {
                String[] tempArray = bufferedReader.readLine().split("=");
                parameter[i] = tempArray[1];
            }
        }catch (IOException e) {
            System.out.print(e.getMessage());
            throw new RuntimeException(e);
        }
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(parameter[0]);
            dataSource.setJdbcUrl(parameter[1]);
            dataSource.setUser(parameter[2]);
            dataSource.setPassword(parameter[3]);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return dataSource;

    }
    /**Configure SessionFactory Properties
     * @return FactoryBean{SessionFactory} */
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("org.artyomnikitin.spring.dto");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", parameter[4]);
        hibernateProperties.setProperty("hibernate.show_sql", parameter[5]);

        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }
    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());//Unwrap
        return transactionManager;
    }
}
