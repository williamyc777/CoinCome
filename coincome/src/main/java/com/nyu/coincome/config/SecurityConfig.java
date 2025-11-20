package com.nyu.coincome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭所有安全拦截
        http
                .csrf(csrf -> csrf.disable())      // 关闭 CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()     // 所有请求都允许
                )
                .formLogin(form -> form.disable()) // 关闭登录表单
                .httpBasic(basic -> basic.disable()); // 关闭 basic auth

        return http.build();
    }
}
