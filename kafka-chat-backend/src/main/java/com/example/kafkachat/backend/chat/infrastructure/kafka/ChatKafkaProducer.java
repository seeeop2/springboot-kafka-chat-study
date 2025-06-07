package com.example.kafkachat.backend.chat.infrastructure.kafka;

import com.example.kafkachat.backend.chat.dto.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka를 통해 채팅 메시지를 전송하는 프로듀서 클래스입니다.
 * 이 클래스는 메시지를 JSON 형식으로 직렬화하여 지정된 Kafka 토픽으로 전송합니다.
 */
@RequiredArgsConstructor
@Component
public class ChatKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 주어진 채팅 메시지를 Kafka의 'chat-room' 토픽으로 전송합니다.
     * 메시지는 JSON 형식으로 직렬화되며, 채팅방 ID를 키로 사용합니다.
     *
     * @param chatMessage 전송할 채팅 메시지 객체
     * @throws RuntimeException 메시지 직렬화 실패 시 발생하는 예외
     */
    public void send(ChatMessage chatMessage) {
        try {
            // ChatMessage 객체를 JSON 문자열로 직렬화합니다.
            String jsonMessage = objectMapper.writeValueAsString(chatMessage);

            // 직렬화된 메시지를 Kafka의 'chat-room' 토픽으로 전송합니다.
            kafkaTemplate.send("chat-room", chatMessage.getRoomId(), jsonMessage);

            // 메시지 전송 성공 로그를 출력합니다.
            System.out.println("Kafka 전송 완료 → " + jsonMessage);
        } catch (JsonProcessingException e) {
            // 직렬화 실패 시 런타임 예외를 발생시킵니다.
            throw new RuntimeException("Kafka 메시지 직렬화 실패", e);
        }
    }
}
