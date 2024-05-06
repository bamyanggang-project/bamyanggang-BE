package jjon.bamyanggang.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jjon.bamyanggang.game.repository.ChatRepository;
import jjon.bamyanggang.model.ChatDTO;
import jjon.bamyanggang.model.RoomUserInfo;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	// [채팅방 생성]
	public void createChatRoom(RoomUserInfo roomUserInfo) {
		System.out.println("[채팅방 생성] Service 시작!");
		chatRepository.createChatRoom(roomUserInfo);
	}
	
	// [채팅방 입장]
	public void joinChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 입장] Service 시작!");
		chatRepository.joinChatRoom(chatDTO);
	}
	
	// [채팅방 퇴장]
	public void exitChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 퇴장] Service 시작!");
		chatRepository.exitChatRoom(chatDTO);
	}
	
	// [메시지 전송]
	public void sendMessage(ChatDTO chatDTO) {
		System.out.println("[메시지 전송] Service 시작!");
		chatRepository.sendMessage(chatDTO);
	}
	
}
