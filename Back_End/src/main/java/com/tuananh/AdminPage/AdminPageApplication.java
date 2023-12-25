package com.tuananh.AdminPage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AdminPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminPageApplication.class, args);
    }

}
