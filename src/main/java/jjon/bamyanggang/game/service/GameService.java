package jjon.bamyanggang.game.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jjon.bamyanggang.game.mapper.GameMapper;
import jjon.bamyanggang.model.MafiaRole;
import jjon.bamyanggang.model.RoomUserInfo;

@Service
public class GameService implements GameMapper{
	
	@Autowired
	private GameMapper gameMapper;
	
	// [게임시작]
	@Transactional
	public List<RoomUserInfo> gameStart(MafiaRole mafiaRole) {
		System.out.println("[게임시작] Service 시작!");
		// [게임시작] mafia_role 조회 (role 변경 전)
		List<RoomUserInfo> getUserInfo = getUserInfo(mafiaRole);
		// 역할 목록 생성 (시민1~5, 마피아)
		List<String> roleList = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			roleList.add("시민");
		}
		roleList.add("마피아");
		// 랜덤 역할 부여
		Collections.shuffle(roleList);
		//
		System.out.println(roleList);
		// roleList index
		int roleListIdx = 0;
		// setRole parameter
		Map<String, Object> setRoleMap = new HashMap<String, Object>();
		for (RoomUserInfo roomUserInfo : getUserInfo) {
			String roomUserRole= roleList.get(roleListIdx);
			String roomUserId = roomUserInfo.getUserId();
			setRoleMap.put("role", roomUserRole);
			setRoleMap.put("userId", roomUserId);
			// [게임시작] 역할 부여
			setRole(setRoleMap);
			roleListIdx++;
		}
		// [게임시작] mafia_role 조회 (role 변경 후)
		getUserInfo = getUserInfo(mafiaRole);
		
		return getUserInfo;
	}
	
	// [게임시작] mafia_role 조회
	@Override
	public List<RoomUserInfo> getUserInfo(MafiaRole mafiaRole) {
		return gameMapper.getUserInfo(mafiaRole);
	}
	
	// [게임시작] 역할 부여
	@Override
	public void setRole(Map<String, Object> map) {
		gameMapper.setRole(map);
	}
	
}
