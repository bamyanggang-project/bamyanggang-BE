package jjon.bamyanggang.model;

import lombok.Data;

@Data
// 채팅 내용을 위한 DTO
public class ChatUser {
	
	private String roomId; // 방 번호
	private String sender; // 채팅을 보낸 사람
	private String message; // 메시지
	
}
