package jjon.bamyanggang.game.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Repository;

import jjon.bamyanggang.model.ChatDTO;
import jjon.bamyanggang.model.ChatRoom;
import jjon.bamyanggang.model.ChatUser;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
	
	private final SimpMessageSendingOperations messaging;
	
	// 생성된 채팅방을 저장하는 용도
	private Map<String, ChatRoom> chatRoomMap = new LinkedHashMap<String, ChatRoom>();
	
	// [채팅방 생성]
	public String createChatRoom(String roomNo) {
		System.out.println("[채팅방 생성] Repository 시작!");
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setRoomId(UUID.randomUUID().toString() + roomNo);
		chatRoom.setRoomNo(roomNo);
		String roomId = chatRoom.getRoomId();
		chatRoomMap.put(roomId, chatRoom);
		System.out.println(chatRoom.getRoomId());
		System.out.println("[채팅방 생성] 끝!");
		return roomId;
	}
	
	// [채팅방 찾기]
	public ChatRoom getChatRoom(String roomId) {
		System.out.println("[채팅방 찾기] Repository 시작!");
		return chatRoomMap.get(roomId);
	}
	
	// [채팅방 입장]
	public List<String> joinChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 입장] Repository 시작!");
		String roomId = chatDTO.getRoomId();
		ChatRoom chatRoom = chatRoomMap.get(roomId);
		int no = 1;
		System.out.println("123");
		List<String> userNicknmList = chatDTO.getUserNicknmList();
		for (String userNicknm : userNicknmList) {
			String userUUID = UUID.randomUUID().toString() + userNicknm + no;
			System.out.println("userUUID : " + userUUID);
			chatRoom.setUserCnt(chatRoom.getUserCnt()+1);
			chatRoom.getUserList().put(userUUID, userNicknm);
			no++;
		}
		System.out.println("현재 cnt : " + chatRoom.getUserCnt());
		System.out.println("[채팅방 입장] 끝!");	
		return userNicknmList;
	}
	
	// [메시지 전송]
	public ChatUser sendMessage(ChatUser chatUser) {
		System.out.println("[메시지 전송] Repository 시작!");
		String roomId = chatUser.getRoomId();
		String message = chatUser.getMessage();
		messaging.convertAndSend("/sub/"+ roomId, message);
		System.out.println(roomId);
		return chatUser;
	}
	
	// [채팅방 퇴장]
	public List<String> exitChatRoom(ChatDTO chatDTO) {
		System.out.println("[채팅방 퇴장] Repository 시작!");
		String roomId = chatDTO.getRoomId();
		ChatRoom chatRoom = chatRoomMap.get(roomId);
		List<String> userNicknmList = chatDTO.getUserNicknmList();
		Iterator<Map.Entry<String, Object>> userIterator = chatRoom.getUserList().entrySet().iterator();
	    while (userIterator.hasNext()) {
	        Map.Entry<String, Object> userEntry = userIterator.next();
	        if (userNicknmList.contains(userEntry.getValue())) {
	        	userIterator.remove();
	            chatRoom.setUserCnt(chatRoom.getUserCnt() - 1);
	        }
	    }
		if (chatRoom.getUserCnt() == 0) {
			chatRoomMap.remove(roomId);
		}
		System.out.println("[채팅방 퇴장] 끝!");
		return userNicknmList;
	}
	
	// [채팅방 목록]
	public List<ChatRoom> getChatRoomList() {
		System.out.println("[채팅방 목록] Repository 시작!");
		List<ChatRoom> getChatRoomList = new ArrayList<ChatRoom>();
		getChatRoomList = (List<ChatRoom>) chatRoomMap.values();
		System.out.println(chatRoomMap.values());
		return getChatRoomList;
	}
	
	
}
