package com.example.kafkachat.backend.core.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * WebSocket 메시지 전송을 담당하는 컴포넌트
 *
 * SimpMessagingTemplate을 이용해 지정된 destination(채널)으로 메시지를 전송합니다.
 * 예외 발생 시 로깅 처리로 장애 상황을 추적할 수 있습니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketSender {

    // WebSocket 메시지 전송을 위한 템플릿 (Spring에서 주입)
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * destination(채널)으로 payload(메시지)를 전송합니다.
     *
     * @param destination 메시지를 보낼 목적지(예: /topic/chat)
     * @param payload 전송할 데이터(객체)
     */
    public void send(String destination, Object payload) {
        try {
            messagingTemplate.convertAndSend(destination, payload); // 메시지 전송
            log.info("WebSocket 전송 - dest: {}, payload: {}", destination, payload); // 성공 로그
        } catch (Exception e) {
            log.error("WebSocket 전송 실패 - dest: {}, error: {}", destination, e.getMessage()); // 실패 로그
        }
    }

}
