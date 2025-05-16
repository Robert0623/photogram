package com.hwoo.photogram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable());

        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/",
                                "/user/**",
                                "/image/**",
                                "/subscribe/**",
                                "/comment/**",
                                "/api/**")
                        .authenticated()
                        .anyRequest().permitAll());

        http.formLogin(form ->
                form.loginPage("/auth/signin") // GET
                        .loginProcessingUrl("/auth/signin") // POST --> 스프링 시큐리티가 로그인 프로세스 진행
                        .defaultSuccessUrl("/"));

        return http.build();
    }
}
