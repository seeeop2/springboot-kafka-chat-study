package com.example.kafkachat.backend.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS(Cross-Origin Resource Sharing) 설정 클래스입니다.
 * 프론트엔드와 백엔드 간의 도메인 간 요청을 허용하기 위한 Spring CORS 필터를 등록합니다.
 */
@Configuration
public class CorsConfig {

    /**
     * CORS(Cross-Origin Resource Sharing) 설정을 위한 필터 빈을 등록합니다.
     * 프론트엔드(다른 도메인)에서 백엔드 API에 접근할 수 있도록 허용합니다.
     */
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 자격 증명(쿠키, 인증정보) 허용
        config.setAllowCredentials(true);
        // 모든 도메인에서의 요청 허용 (패턴 기반)
        config.addAllowedOriginPattern("*");
        // 모든 헤더 허용
        config.addAllowedHeader("*");
        // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
        config.addAllowedMethod("*");
        // Authorization 헤더를 응답에 노출
        config.addExposedHeader("Authorization");
        // 모든 경로에 대해 위 설정 적용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
