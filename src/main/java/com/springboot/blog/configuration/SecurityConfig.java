package com.springboot.blog.configuration;

import com.springboot.blog.authenticationUtils.AuthenticationFilter;
import com.springboot.blog.authenticationUtils.CustomAuthenticationEntryPoint;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme( name = "Authorization Required", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme="bearer" )
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private AuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, AuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    // method to encode the password using Bcrypt algorithm
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // enable http basic authentication
        httpSecurity.csrf(
                (csrf) -> csrf.disable()).authorizeHttpRequests(
                        (authorize)-> authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/post/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/post/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/api/post/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

       // making sure that our authentication filter class executes before rest of the filter classes
        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
