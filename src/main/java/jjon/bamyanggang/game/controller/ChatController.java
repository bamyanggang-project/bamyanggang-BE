package jjon.bamyanggang.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;


import jjon.bamyanggang.game.service.ChatService;
import jjon.bamyanggang.model.ChatDTO;
import jjon.bamyanggang.model.RoomUserInfo;

@RestController
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	// [채팅방 생성]
	@MessageMapping("createChatRoom")
	public void creatChatRoom(@Payload RoomUserInfo roomUserInfo) {
		System.out.println("[채팅방 생성] Controller 시작!");
		try {
			chatService.createChatRoom(roomUserInfo);
			System.out.println("[채팅방 생성] 성공!");
			
		} catch (Exception e) {
			System.out.println("[채팅방 생성] 실패ㅠ");
		}
	}
	
	// [채팅방 입장]
	@MessageMapping("joinChatRoom")
	public void joinChatRoom(@Payload ChatDTO chatDTO) {
		System.out.println("[채팅방 입장] Controller 시작!");
		try {
			chatService.joinChatRoom(chatDTO);
			System.out.println("[채팅방 입장] 성공!");
		} catch (Exception e) {
			System.out.println("채팅방 입장] 실패ㅠ");
		}
	}
	
	// [채팅방 퇴장]
	@MessageMapping("exitChatRoom")
	public void exitChatRoom(@Payload ChatDTO chatDTO) {
		System.out.println("[채팅방 퇴장] Controller 시작!");
		try {
			chatService.exitChatRoom(chatDTO);
			System.out.println("[채팅방 퇴장] 성공!");
		} catch (Exception e) {
			System.out.println("[채팅방 퇴장] 실패ㅠ");
		}
	}
	
	// [메시지 전송]
	@MessageMapping("sendMessage")
	public void sendMessage(@Payload ChatDTO chatDTO) {
		System.out.println("[메시지 전송] Controller 시작!");
		try {
			chatService.sendMessage(chatDTO);
			System.out.println("[메시지 전송] 성공!");
		} catch (Exception e) {
			System.out.println("[메시지 전송] 실패ㅠ");
		}
	}
	
}
