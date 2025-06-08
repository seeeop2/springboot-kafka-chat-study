package com.example.kafkachat.backend.chat.service;

import com.example.kafkachat.backend.chat.dto.ChatMessage;
import com.example.kafkachat.backend.chat.infrastructure.kafka.ChatKafkaProducer;
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

    /**
     * 주어진 채팅 메시지를 Kafka로 전송합니다.
     *
     * @param chatMessage 전송할 채팅 메시지 객체
     */
    public void sendMessage(ChatMessage chatMessage) {
        chatKafkaProducer.send(chatMessage);
    }
}
