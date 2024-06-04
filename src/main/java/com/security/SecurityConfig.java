package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(authorize -> {
//            try {
//                authorize.requestMatchers("/", "/add").permitAll()
//                        .anyRequest().authenticated()
//                        .and()
//                        .formLogin(Customizer.withDefaults())
//                        .httpBasic(Customizer.withDefaults());
//            } catch (Exception e) {
//                throw new RuntimeException();
//            }
//
//        });
//        return httpSecurity.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> {
            try {
                authorize.requestMatchers("/","/add","/save").permitAll()
                        .requestMatchers("/update/**", "/delete/**").hasAuthority("ADMIN")
//                        .requestMatchers("/course/delete/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                        .formLogin(Customizer.withDefaults())
                        .httpBasic(Customizer.withDefaults());
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
        return httpSecurity.build();
    }
}
