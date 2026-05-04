package com.example.smu;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Corsconfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera TODAS as rotas da sua API
                .allowedOrigins("*") // Libera o seu Live Server (127.0.0.1)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // O OPTIONS é o que evita o erro de Preflight!
                .allowedHeaders("*"); // Libera qualquer cabeçalho de segurança
    }
}