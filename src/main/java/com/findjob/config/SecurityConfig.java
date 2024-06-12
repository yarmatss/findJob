package com.findjob.config;

import com.findjob.security.CustomAuthenticationSuccessHandler;
import com.findjob.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService,
                          CustomAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.personDetailsService = personDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/profile/*", "/auth/registration", "/auth/login",
                                        "offer/*", "/", "images/**", "resume/**", "task/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/employer/**").hasRole("EMPLOYER")
                                .requestMatchers("/candidate/**").hasRole("CANDIDATE")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/auth/login")
                                .successHandler(authenticationSuccessHandler)
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
