package org.codegym.springsecurity.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration  // Đánh dấu đây là một lớp cấu hình cho Spring
@EnableTransactionManagement  // Kích hoạt quản lý giao dịch với Spring
@EnableJpaRepositories(basePackages = "org.codegym.springsecurity.repository")  // Kích hoạt Spring Data JPA và chỉ định package chứa các repository
public class JpaConfig {

    // Cấu hình DataSource, cung cấp kết nối tới cơ sở dữ liệu
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");  // Đăng ký driver JDBC
        dataSource.setUrl("jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC");  // URL kết nối đến cơ sở dữ liệu
        dataSource.setUsername("root");  // Tên người dùng cơ sở dữ liệu
        dataSource.setPassword("123456@Abc");  // Mật khẩu cơ sở dữ liệu
        return dataSource;
    }

    // Cấu hình LocalContainerEntityManagerFactoryBean, chịu trách nhiệm tạo EntityManager
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());  // Liên kết với DataSource đã cấu hình
        em.setPackagesToScan("org.codegym.springsecurity.model");  // Các package chứa các entity classes
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);  // Thiết lập adapter cho Hibernate
        em.setJpaProperties(hibernateProperties());  // Cung cấp các thuộc tính JPA

        return em;
    }

    // Cung cấp các thuộc tính Hibernate như dialect và chế độ hiển thị SQL
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");  // Đặt dialect cho Hibernate phù hợp với MySQL 8
        properties.setProperty("hibernate.hbm2ddl.auto", "update");  // Tự động cập nhật cấu trúc cơ sở dữ liệu dựa trên các entity
        properties.setProperty("hibernate.show_sql", "true");  // Hiển thị các câu lệnh SQL trong log
        properties.setProperty("hibernate.format_sql", "true");  // Định dạng SQL để dễ đọc hơn trong log
        return properties;
    }

    // Cấu hình JpaTransactionManager, quản lý các giao dịch JPA
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());  // Liên kết với EntityManagerFactory đã cấu hình
        return transactionManager;
    }
}