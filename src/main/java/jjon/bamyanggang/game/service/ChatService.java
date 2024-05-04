package jjon.bamyanggang.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jjon.bamyanggang.game.repository.ChatRepository;
import jjon.bamyanggang.model.ChatDTO;
import jjon.bamyanggang.model.ChatRoom;
import jjon.bamyanggang.model.ChatUser;
import jjon.bamyanggang.model.RoomUserInfo;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	// [채팅방 생성]
	public String createChatRoom(RoomUserInfo roomUserInfo) {
		System.out.println("[채팅방 생성] Service 시작!");
		String roomNo = String.valueOf(roomUserInfo.getRoomNo());
		String roomId = chatRepository.createChatRoom(roomNo);
		return roomId;
	}
	
	// [채팅방 찾기]
	public ChatRoom getChatRoom(String roomId) {
		System.out.println("[채팅방 찾기] Service 시작!");
		return chatRepository.getChatRoom(roomId);
	}
	
	// [채팅방 입장]
	public List<String> joinChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 입장] Service 시작!");
		return chatRepository.joinChatRoom(chatDTO);
	}
	
	// [메시지 전송]
	public ChatUser  sendMessage(ChatUser chatUser) {
		System.out.println("[메시지 전송] Service 시작!");
		return chatRepository.sendMessage(chatUser);
	}
	
	// [채팅방 퇴장]
	public List<String> exitChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 퇴장] Service 시작!");
		return chatRepository.exitChatRoom(chatDTO);
	}
	
	// [채팅방 목록]
	public List<ChatRoom> getChatRoomList() {
		System.out.println("[채팅방 목록] Service 시작!");
		return chatRepository.getChatRoomList();
	}
	
	
}
