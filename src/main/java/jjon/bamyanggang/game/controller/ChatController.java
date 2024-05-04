package jjon.bamyanggang.game.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jjon.bamyanggang.game.service.ChatService;
import jjon.bamyanggang.model.ChatDTO;
import jjon.bamyanggang.model.ChatRoom;
import jjon.bamyanggang.model.ChatUser;
import jjon.bamyanggang.model.RoomUserInfo;



@RestController
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	// [채팅방 생성]
	@MessageMapping("createChatRoom")
	public String creatChatRoom(@Payload RoomUserInfo roomUserInfo) {
		System.out.println("[채팅방 생성] Controller 시작!");
		Gson gson = new Gson();
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			String roomId = chatService.createChatRoom(roomUserInfo);
			responseBody.put("msg", "[채팅방 생성] 성공!");
			responseBody.put("roomId", roomId);
			
			return gson.toJson(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[채팅방 생성] 실패ㅠ");
			
			return gson.toJson(responseBody);
		}
	}
	
	// [채팅방 찾기]
	@MessageMapping("getChatRoom")
	public String getChatRoom(@Payload String roomId) {
		System.out.println("[채팅방 찾기] Controller 시작!");
		Gson gson= new Gson();
		Map<String, Object> responseBody =new HashMap<String, Object>();
		try {
			ChatRoom chatRoom = chatService.getChatRoom(roomId);
			responseBody.put("msg", "[채팅방 찾기] 성공!");
			responseBody.put("ChatRoom", chatRoom);
			
			return gson.toJson(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[채팅방 찾기] 실패ㅠ");
			
			return gson.toJson(responseBody);
		}
		
	}
	
	
	// [채팅방 입장]
	@MessageMapping("joinChatRoom")
	public String joinChatRoom(@Payload ChatDTO chatDTO) {
		System.out.println("[채팅방 입장] Controller 시작!");
		Gson gson = new Gson();
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			List<String> userNicknmList = chatService.joinChatRoom(chatDTO);
			responseBody.put("msg", "[채팅방 입장] 성공!");
			responseBody.put("userNicknmList", userNicknmList);
			
			return gson.toJson(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[채팅방 입장] 성공!");
			return gson.toJson(responseBody);
		}
	}
	
	// [메시지 전송]
	@MessageMapping("sendMessage")
	public String sendMessage(@Payload ChatUser chatUser) {
		System.out.println("[메시지 전송] Controller 시작!");
		Gson gson = new Gson();
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			ChatUser msgInfo = chatService.sendMessage(chatUser);
			responseBody.put("msg", "[메시지 전송] 성공!");
			responseBody.put("commInfo", msgInfo);
			
			return gson.toJson(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[메시지 전송] 실패ㅠ");
			
			return gson.toJson(responseBody);
		}
	}
	
	// [채팅방 퇴장]
	@MessageMapping("exitChatRoom")
	public String exitChatRoom(@Payload ChatDTO chatDTO) {
		System.out.println("[채팅방 퇴장] Controller 시작!");
		Gson gson = new Gson();
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			List<String> userNicknmList = chatService.exitChatRoom(chatDTO);
			responseBody.put("msg", "[채팅방 퇴장] 성공!");
			responseBody.put("userNicknmList", userNicknmList);
			
			return gson.toJson(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[채팅방 퇴장] 실패ㅠ");
			
			return gson.toJson(responseBody);
		}
	}
	
	// [채팅방 목록]
	@MessageMapping("getChatRoomList")
	public String getChatRoomList() {
		System.out.println("[채팅방 목록] Controller 시작!");
		Gson gson = new Gson();
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			List<ChatRoom> getChatRoomList = chatService.getChatRoomList();
			responseBody.put("msg", "[채팅방 목록] 성공!");
			responseBody.put("getChatRoomList", getChatRoomList);
			
			return gson.toJson(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[채팅방 목록] 실패ㅠ");

			return gson.toJson(responseBody);
		}
	}
	
	
}
