package jjon.bamyanggang.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
// 채팅룸을 위한 DTO
public class ChatRoom {
	private String roomId;
	private String roomNo;
	private int userCnt;
	private Map<String, Object> userList = new HashMap<String, Object>();
	
}
