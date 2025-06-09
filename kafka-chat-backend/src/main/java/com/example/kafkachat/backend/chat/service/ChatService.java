package com.example.kafkachat.backend.chat.service;

import com.example.kafkachat.backend.chat.dto.ChatMessage;
import com.example.kafkachat.backend.chat.infrastructure.kafka.ChatKafkaProducer;
import com.example.kafkachat.backend.core.websocket.WebSocketSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 채팅 서비스 클래스입니다.
 * 이 클래스는 채팅 메시지를 Kafka로 전송하는 역할을 합니다.
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatKafkaProducer chatKafkaProducer;
    private final WebSocketSender webSocketSender;

    /**
     * 주어진 채팅 메시지를 Kafka로 전송합니다.
     *
     * @param chatMessage 전송할 채팅 메시지 객체
     */
    public void sendMessage(ChatMessage chatMessage) {
        chatKafkaProducer.send(chatMessage);
    }

    /**
     * Kafka로부터 수신한 채팅 메시지를 처리합니다.
     * 이 메서드는 DB 저장 로직을 포함할 수 있으며, 현재는 생략되어 있습니다.
     *
     * @param message 수신한 채팅 메시지 객체
     */
    public void handleReceivedMessage(ChatMessage message) {
        // 1. DB 저장 로직 (선택, 지금은 생략)

        // 2. WebSocket 실시간 메시지 전송
        String destination = "/topic/chat/" + "room-" + message.getRoomId();
        webSocketSender.send(destination, message);
    }
}
