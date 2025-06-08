package com.example.kafkachat.backend.chat.controller;

import com.example.kafkachat.backend.chat.dto.ChatMessage;
import com.example.kafkachat.backend.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 채팅 관련 HTTP 요청을 처리하는 컨트롤러입니다.
 * 클라이언트로부터 채팅 메시지를 받아 서비스 계층에 전달합니다.
 */
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    /**
     * 채팅 메시지 전송 API 엔드포인트입니다.
     * 클라이언트로부터 받은 메시지를 서비스에 전달하여 Kafka로 전송합니다.
     *
     * @param message 클라이언트에서 전달된 채팅 메시지
     * @return 전송 결과 메시지
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody ChatMessage message) {
        chatService.sendMessage(message);
        return ResponseEntity.ok("메시지 전송 완료");
    }
}
