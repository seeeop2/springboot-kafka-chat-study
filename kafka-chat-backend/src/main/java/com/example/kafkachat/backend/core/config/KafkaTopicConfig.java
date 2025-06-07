package com.example.kafkachat.backend.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka 토픽 설정을 담당하는 설정 클래스입니다.
 * 이 클래스에서는 채팅방 메시지를 위한 Kafka 토픽을 생성합니다.
 *
 * Spring Boot 애플리케이션이 실행될 때,
 * 이 설정에 따라 'chat-room' 토픽이 자동으로 생성됩니다.
 * (Kafka 브로커가 실행 중이어야 하며, 이미 존재하지 않을 경우에만 생성됨)
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * 채팅방 메시지 처리를 위한 Kafka 토픽을 생성합니다.
     * @return chat-room 토픽 설정
     */
    @Bean
    public NewTopic chatRoomTopic(){
        return TopicBuilder
                .name("chat-room") // 토픽 이름(chat-room)
                .partitions(1) // 파티션 수: 메시지가 분산 저장되는 단위 (성능 및 확장성에 영향)
                .replicas(1) // 복제본 수: 장애 대비를 위한 데이터 복제 개수
                .build();
    }
}
