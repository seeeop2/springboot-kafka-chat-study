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
     * 'chat-room' 토픽에서 유저 메시지를 수신하는 리스너 메서드입니다.
     * ConsumerRecord를 통해 메시지와 메타데이터를 전달받아 ChatMessage 객체로 변환 후 처리합니다.
     *
     * @param record Kafka ConsumerRecord 객체 (key, value, topic 등 메타데이터 포함)
     */
    @KafkaListener(topics = "chat-room", groupId = "chat-group")
    public void userMessageListener(ConsumerRecord<String, String> record) {
        try {
            // Kafka에서 받은 JSON 문자열을 ChatMessage 객체로 변환
            ChatMessage message = objectMapper.readValue(record.value(), ChatMessage.class);
            // 수신한 메시지 콘솔 출력 (실제 서비스에서는 별도 처리 필요)
            System.out.println("유저용 Kafka 수신 메시지: " + message);
        } catch (Exception e) {
            // 역직렬화 또는 처리 중 예외 발생 시 에러 로그 출력
            System.err.println("[chat-group] 처리 실패: " + e.getMessage());
        }
    }

    /**
     * 'chat-room' 토픽에서 로그 메시지를 수신하는 리스너 메서드입니다.
     * @param record Kafka ConsumerRecord 객체로, 메시지와 메타데이터를 포함합니다.
     */
    @KafkaListener(topics = "chat-room", groupId = "log-group")
    public void logMessageListener(ConsumerRecord<String, String> record) {
        try {
            ChatMessage message = objectMapper.readValue(record.value(), ChatMessage.class);
            System.out.println("🗃[log-group] 채팅 로그 저장용 수신: " + message);
        } catch (Exception e) {
            System.err.println("[log-group] 처리 실패: " + e.getMessage());
        }
    }
}
