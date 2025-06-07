package com.example.kafkachat.backend.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 채팅 메시지 정보를 담는 DTO 클래스입니다.
 * 각 채팅 메시지는 채팅방 ID, 보낸 사람, 메시지 내용을 포함합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String roomId; // 채팅방 ID
    private String sender; // 보낸 사람(닉네임 등)
    private String message; // 메시지 내용
}
