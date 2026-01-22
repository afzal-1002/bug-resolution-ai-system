package com.pw.edu.pl.master.thesis.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers("/api/wut/users/**").permitAll()
                        .requestMatchers("/api/wut/jira-users/**").permitAll()
                        .requestMatchers("/api/wut/profile/**").permitAll()
                        .requestMatchers("/api/wut/sites/**").permitAll()
                        .requestMatchers("/api/wut/credentials/**").permitAll()
                        .requestMatchers("/api/wut/logs/**").permitAll()

                        .requestMatchers("/api/wut/issues/**").permitAll()
                        .requestMatchers("/api/wut/comments/**").permitAll()
                        .requestMatchers("/api/wut/jira/comment/**").permitAll()
                        .requestMatchers("/api/jira/issues/**").permitAll()

                        .requestMatchers("/api/wut/projects/**").permitAll()

                        .requestMatchers("/api/wut/ai/**").permitAll()

                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/wut/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/wut/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/wut/users/logout").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/wut/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/wut/credentials/me").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/wut/credentials/me/**").permitAll()

                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/refresh").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}