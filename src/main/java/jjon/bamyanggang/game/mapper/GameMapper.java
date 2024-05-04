package jjon.bamyanggang.game.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import jjon.bamyanggang.model.MafiaRole;
import jjon.bamyanggang.model.RoomUserInfo;

@Mapper
public interface GameMapper {
	
	// [게임시작] mafia_role 조회
	public List<RoomUserInfo> getUserInfo(MafiaRole mafiaRole);
	
	// [게임시작] 역할 부여
	public void setRole(Map<String, Object> map);
	
}
