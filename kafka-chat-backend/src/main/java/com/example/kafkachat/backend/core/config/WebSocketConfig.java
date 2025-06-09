package com.example.kafkachat.backend.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 및 STOMP 메시지 브로커 설정 클래스
 *
 * 이 클래스는 WebSocket을 통한 실시간 메시징을 위해
 * STOMP 프로토콜과 메시지 브로커를 설정합니다.
 */
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * STOMP 엔드포인트를 등록합니다.
     * 클라이언트는 해당 엔드포인트로 WebSocket 연결을 시도합니다.
     * SockJS를 지원하여 WebSocket 미지원 환경에서도 동작합니다.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat") // 클라이언트 연결 주소
                .setAllowedOriginPatterns("*") // 모든 오리진 허용 (CORS)
                .withSockJS(); // WebSocket 미지원 시 SockJS로 대체
    }

    /**
     * 메시지 브로커를 구성합니다.
     * /topic으로 시작하는 경로는 브로커가 처리(구독),
     * /app으로 시작하는 경로는 서버의 @MessageMapping으로 라우팅됩니다.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // 메시지 구독 경로 (브로커 처리)
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트 전송 prefix (컨트롤러 처리)
    }
}
