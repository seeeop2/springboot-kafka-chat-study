package com.example.kafkachat.backend.chat.infrastructure.kafka;

import com.example.kafkachat.backend.chat.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka로부터 채팅 메시지를 수신하는 리스너 클래스입니다.
 * 이 클래스는 'chat-room' 토픽에서 메시지를 수신합니다.
 */
@Component
@RequiredArgsConstructor
public class ChatKafkaConsumer {

    private final ObjectMapper objectMapper;

    /**
     * Kafka로부터 채팅 메시지를 수신하는 리스너 메서드입니다.
     * 'chat-room' 토픽에서 메시지를 수신하고, 이를 ChatService로 전달합니다.
     *
     * @param record Kafka에서 수신한 메시지 레코드
     */
    @KafkaListener(topics = "chat-room", groupId = "chat-group")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            String json = record.value(); // Kafka 메시지 값
            ChatMessage message = objectMapper.readValue(json, ChatMessage.class);

            System.out.println("Kafka 수신 메시지: " + message);
        } catch (Exception e) {
            System.err.println("Kafka 메시지 처리 실패: " + e.getMessage());
        }
    }
}
