package jjon.bamyanggang.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("room_user_info")
public class RoomUserInfo {
	
	private int roomNo;
	private String role;
	private int master;
	private String userId;
	private String userNicknm;
	private String userImg;
	
}
