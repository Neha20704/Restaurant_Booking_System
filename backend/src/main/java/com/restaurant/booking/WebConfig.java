package com.restaurant.booking; // Make sure this matches your actual package

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indicates this class contains Spring configuration
public class WebConfig {

    // Bean to configure Cross-Origin Resource Sharing (CORS) settings
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Apply CORS settings to all /api/* endpoints
                        .allowedOrigins("http://localhost:8082") // Allow frontend requests from this origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow any headers
                        .allowCredentials(true); // Allow cookies and credentials
            }
        };
    }
}
