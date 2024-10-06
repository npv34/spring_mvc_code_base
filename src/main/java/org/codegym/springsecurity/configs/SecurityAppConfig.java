package org.codegym.springsecurity.configs;

import org.codegym.springsecurity.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")  // Bỏ qua cảnh báo về việc sử dụng API bị deprecated (WebSecurityConfigurerAdapter)
@EnableWebSecurity  // Kích hoạt Spring Security cho ứng dụng
public class SecurityAppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;  // Tự động tiêm (inject) bean PasswordEncoder để mã hóa mật khẩu

    @Autowired
    private UserServiceImpl userService;

    // Cấu hình xác thực người dùng trong bộ nhớ (in-memory authentication)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    // Cấu hình bảo mật HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Vô hiệu hóa bảo vệ CSRF (chỉ làm trong trường hợp cần thiết)
                .authorizeRequests()  // Bắt đầu cấu hình phân quyền truy cập
                .antMatchers("/home", "/login", "/submit-login").permitAll()  // Cho phép truy cập không cần xác thực tới các URL này
                .anyRequest().authenticated()  // Yêu cầu xác thực đối với tất cả các yêu cầu khác
                .and()
                .formLogin()  // Cấu hình đăng nhập qua form
                .loginPage("/login")  // Đường dẫn đến trang đăng nhập tùy chỉnh
                .loginProcessingUrl("/submit-login")  // Đường dẫn xử lý form đăng nhập (tương ứng với action trong form login)
                .defaultSuccessUrl("/admin/dashboard", true)  // Chuyển hướng tới "/admin/dashboard" sau khi đăng nhập thành công
                .failureUrl("/login?error=true")  // Chuyển hướng đến trang đăng nhập kèm theo lỗi nếu đăng nhập thất bại
                .permitAll()  // Cho phép tất cả người dùng truy cập vào các trang liên quan đến đăng nhập và logout
                .and()
                .logout()  // Cấu hình đăng xuất
                .permitAll();  // Cho phép bất kỳ ai có thể đăng xuất
    }
}