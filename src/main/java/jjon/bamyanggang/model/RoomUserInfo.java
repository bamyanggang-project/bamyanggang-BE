package jjon.bamyanggang.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("room_user_info")
public class RoomUserInfo {
	
	private String userId;
	private String userNickNm;
	private String userImg;
	private int master;
	
}
