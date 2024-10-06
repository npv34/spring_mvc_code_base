package org.codegym.springsecurity.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.codegym.springsecurity")
public class WebConfig implements WebMvcConfigurer {
    // Cấu hình resolver cho Thymeleaf, xác định đường dẫn tới các template
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");  // Đường dẫn tới thư mục chứa các file HTML
        templateResolver.setSuffix(".html");  // Đuôi của các template sẽ là .html
        templateResolver.setTemplateMode(TemplateMode.HTML);  // Chế độ của template là HTML
        templateResolver.setCharacterEncoding("UTF-8");  // Thiết lập encoding là UTF-8 để hiển thị tiếng Việt hoặc ký tự đặc biệt
        return templateResolver;
    }

    // Cấu hình template engine cho Thymeleaf, sử dụng templateResolver đã được cấu hình ở trên
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());  // Liên kết với resolver để xử lý các template
        return templateEngine;
    }

    // Cấu hình view resolver cho Thymeleaf, chịu trách nhiệm render các view
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());  // Sử dụng template engine đã được cấu hình
        viewResolver.setCharacterEncoding("UTF-8");  // Đảm bảo view sử dụng đúng encoding UTF-8
        return viewResolver;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();  // Tạo RestTemplate để gửi request đến API
    }


    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
