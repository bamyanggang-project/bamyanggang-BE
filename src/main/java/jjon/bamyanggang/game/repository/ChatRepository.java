package jjon.bamyanggang.game.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Repository;

import jjon.bamyanggang.model.ChatDTO;
import jjon.bamyanggang.model.ChatRoom;
import jjon.bamyanggang.model.RoomUserInfo;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
	
	private final SimpMessageSendingOperations messaging;
	
	// [채팅창 목록]
	private Map<String, ChatRoom> chatRoomMap = new LinkedHashMap<String, ChatRoom>();
	
	// [채팅방 생성]
	public void createChatRoom(RoomUserInfo roomUserInfo) {
		System.out.println("[채팅방 생성] Repository 시작!");
		ChatRoom chatRoom = new ChatRoom();
		String roomNo = String.valueOf(roomUserInfo.getRoomNo());
		chatRoom.setRoomNo(roomNo);
		String chatRoomNo = chatRoom.getRoomNo();
		System.out.println("chatRoomNo : " + chatRoomNo);
		chatRoomMap.put(chatRoomNo, chatRoom);
	}
	
	// [채팅방 입장]
	public void joinChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 입장] Repository 시작!");
		String chatRoomNo = chatDTO.getRoomNo();
		ChatRoom chatRoom = chatRoomMap.get(chatRoomNo);
		chatRoom.setUserCnt(chatRoom.getUserCnt()+1);
		int userCnt = chatRoom.getUserCnt();
		System.out.println("userCnt : " + userCnt);
	}
	
	// [채팅방 퇴장]
	public void exitChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 퇴장] Repository 시작!");
		String chatRoomNo = chatDTO.getRoomNo();
		ChatRoom chatRoom = chatRoomMap.get(chatRoomNo);
		chatRoom.setUserCnt(chatRoom.getUserCnt() - 1);
		int userCnt = chatRoom.getUserCnt();
		System.out.println("userCnt : " + userCnt);
		if (chatRoom.getUserCnt() == 0) {
			chatRoomMap.remove(chatRoomNo);
		}
	}
	
	// [메시지 전송]
	public void sendMessage(ChatDTO chatDTO) {
		System.out.println("[메시지 전송] Repository 시작!");
		String chatRoomNo = chatDTO.getRoomNo();
		messaging.convertAndSend("/sub/"+ chatRoomNo, chatDTO);
	}
	
}
