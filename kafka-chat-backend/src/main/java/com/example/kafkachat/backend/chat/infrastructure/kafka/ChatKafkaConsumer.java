package com.example.kafkachat.backend.chat.infrastructure.kafka;

import com.example.kafkachat.backend.chat.dto.ChatMessage;
import com.example.kafkachat.backend.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka로부터 채팅 메시지를 수신하여 처리하는 리스너 클래스입니다.
 * - 'chat-room' 토픽에서 메시지를 수신합니다.
 * - 수신한 메시지는 ChatService로 전달되어 DB 저장, WebSocket 브로드캐스트 등 추가 처리가 가능합니다.
 */
@Component
@RequiredArgsConstructor
public class ChatKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    /**
     * 'chat-room' 토픽에서 유저 메시지를 수신하는 리스너 메서드입니다.
     * Kafka에서 받은 메시지를 ChatMessage 객체로 변환 후 ChatService로 전달합니다.
     *
     * @param record Kafka ConsumerRecord 객체 (key, value, topic 등 메타데이터 포함)
     */
    @KafkaListener(topics = "chat-room", groupId = "chat-group")
    public void userMessageListener(ConsumerRecord<String, String> record) {
        try {
            // Kafka에서 받은 JSON 문자열을 ChatMessage 객체로 변환
            ChatMessage message = objectMapper.readValue(record.value(), ChatMessage.class);
            // 수신한 메시지 콘솔 출력 (운영 환경에서는 로그 관리 필요)
            System.out.println("유저용 Kafka 수신 메시지: " + message);
            // ChatService로 메시지 전달 (DB 저장, WebSocket 전송 등)
            chatService.handleReceivedMessage(message);
        } catch (Exception e) {
            // 역직렬화 또는 처리 중 예외 발생 시 에러 로그 출력
            System.err.println("[chat-group] 처리 실패: " + e.getMessage());
        }
    }

    /**
     * 'chat-room' 토픽에서 로그 메시지를 수신하는 리스너 메서드입니다.
     * 주로 채팅 로그 저장 등 부가적인 용도로 사용됩니다.
     * @param record Kafka ConsumerRecord 객체로, 메시지와 메타데이터를 포함합니다.
     */
    @KafkaListener(topics = "chat-room", groupId = "log-group")
    public void logMessageListener(ConsumerRecord<String, String> record) {
        try {
            // Kafka에서 받은 JSON 문자열을 ChatMessage 객체로 변환
            ChatMessage message = objectMapper.readValue(record.value(), ChatMessage.class);
            // 로그용 메시지 콘솔 출력 (운영 환경에서는 별도 저장 처리 필요)
            System.out.println("🗃[log-group] 채팅 로그 저장용 수신: " + message);
        } catch (Exception e) {
            // 예외 발생 시 에러 로그 출력
            System.err.println("[log-group] 처리 실패: " + e.getMessage());
        }
    }
}
