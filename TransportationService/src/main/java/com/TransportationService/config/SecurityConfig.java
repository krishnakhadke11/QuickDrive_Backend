package com.TransportationService.config;

import com.TransportationService.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
@EnableWebSecurity
public class SecurityConfig {
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /* Here Lazy is used to break the circular dependency of AccessDeniedHandler,JwtAuthenticationFilter,
        handlerExceptionResolver,customAccessDenied*/

    @Autowired
    public SecurityConfig(
            @Lazy AccessDeniedHandler accessDeniedHandler,@Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/driver/**").hasAnyAuthority(Role.DRIVER.name(),Role.ADMIN.name())
                        .requestMatchers("/customer/**").hasAnyAuthority(Role.CUSTOMER.name(),Role.ADMIN.name())
                        .requestMatchers("/fare/**").hasAnyAuthority(Role.CUSTOMER.name(),Role.ADMIN.name())
                        .requestMatchers("/cab/**").hasAnyAuthority(Role.DRIVER.name(),Role.ADMIN.name())
                        .requestMatchers("/ride/**").hasAnyAuthority(Role.CUSTOMER.name(),Role.ADMIN.name())
                        .requestMatchers("/riderequest/accept/**").hasAnyAuthority(Role.DRIVER.name(),Role.ADMIN.name())
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/api-docs").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                // No session is Created for jwt authentications
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(configurer -> configurer.accessDeniedHandler(accessDeniedHandler));

        /*
            Prevents Cross Site Scripting - Injection of malicious scripts into web pages viewed by user
            By implementing the below code it will remove any Xss content from the header if detected
         */
        http.headers(headers ->
                headers.xssProtection(
                        //Enables the XSS protection in the browser
                        //And Instruct to block the page if xss content is detected
                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ).contentSecurityPolicy(
                        //Allows script to be added only from the same origin
                        //Helps to prevent Xss attack by disallowing the External scripts
                        cps -> cps.policyDirectives("script-src 'self'")
                ));
        return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
//        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
//        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        source.registerCorsConfiguration("/**",configuration);
//
//        return source;
//    }
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings (CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins ("http://localhost:4200")
                        .allowedMethods("GET","POST","PUT","DELETE");
            }
        };
    }
}
