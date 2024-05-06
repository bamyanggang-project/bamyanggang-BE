package jjon.bamyanggang.model;

import lombok.Data;

@Data
// 채팅방 생성을 위한 DTO
public class ChatRoom {
	
	private String roomNo; // // 입장한 방 (구독한 채널)
	private int userCnt; // 참여인원
	
}
