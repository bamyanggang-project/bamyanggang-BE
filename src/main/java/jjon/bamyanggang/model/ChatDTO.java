package jjon.bamyanggang.model;

import lombok.Data;

@Data
// 채팅방 입장 / 채팅 / 채팅방 퇴장을 위한 DTO
public class ChatDTO {
	
	private String roomNo; // 입장한 방 (구독한 채널)
	private String userNick; // 메시지를 송신한 사람
	private String message; // 메시지

}
