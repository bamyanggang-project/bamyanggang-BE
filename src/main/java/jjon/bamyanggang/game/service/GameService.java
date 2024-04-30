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
	public List<RoomUserInfo> gameStart(int roomNo) {
		System.out.println("[게임시작] Service 시작!");
		// [게임시작] mafia_role 조회 (role 변경 전)
		List<RoomUserInfo> getUserInfo = getUserInfo(roomNo);
		// 역할 목록 생성 (시민1~5, 마피아)
		List<String> roleList = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			roleList.add("시민");
		}
		roleList.add("마피아");
		// 랜덤 역할 부여
		Collections.shuffle(roleList);
		// 랜덤으로 부여됐는지 확인
		System.out.println(roleList);
		// roleList index
		int roleListIdx = 0;
		// [게임시작] 역할 부여 setParameter
		Map<String, Object> setParameter = new HashMap<String, Object>();
		for (RoomUserInfo roomUserInfo : getUserInfo) {
			String role = roleList.get(roleListIdx);
			System.out.println("role : " + role);
			String userId = roomUserInfo.getUserId();
			System.out.println("userId :" + userId);
			setParameter.put("role", role);
			setParameter.put("userId", userId);
			// [게임시작] 역할 부여
			setRole(setParameter);
			// 6번 반복
			roleListIdx++;
		}
		// [게임시작] mafia_vote table 초기세팅
		initVote(roomNo);
		System.out.println();
		// [게임시작] mafia_role 조회 (role 변경 후)
		getUserInfo = getUserInfo(roomNo);
		
		return getUserInfo;
	}
	
	// [투표]
	@Override
	public void votePlus(MafiaRole mafiaRole) {
		System.out.println("[투표] Service 시작!");
		gameMapper.votePlus(mafiaRole);
	}
	
	// [인게임]
	@Transactional
	public Map<String, Object> resultVote(int roomNo) {
		System.out.println("[인게임] Service 시작!");
		Map<String, Object> resultVote = new HashMap<String, Object>();
		// [인게임] 최대 투표자 수 
		int cntMaxUser = cntMaxUser(roomNo);
		System.out.println("cntMaxUser : " + cntMaxUser);
		// [인게임] 생존자 수
		int survivor = 0;
		// 최대 투표자 수가 1명이면
		if (cntMaxUser == 1) {
			// [인게임] 최대 투표자의 정보
			RoomUserInfo getMaxRole = getMaxRole(roomNo);
			String role = getMaxRole.getRole();
			System.out.println(role);
			String userId = getMaxRole.getUserId();
			System.out.println(userId);
			String userNicknm = getMaxRole.getUserNicknm();
			System.out.println(userNicknm);
			// 최대 투표자가 마피아면
			if (role.equals("마피아")) {
				// [인게임] die
				setRoleSt(getMaxRole);
				resultVote.put("result", 1);
				resultVote.put("userId", userId);
				resultVote.put("userNicknm", userNicknm);
				// [인게임] 승패가 결정남
				delVote(roomNo);
				
				return resultVote;
			} else {
				// [인게임] die
				setRoleSt(getMaxRole);
				// [인게임] 생존자 수
				survivor = cntSurvivor(roomNo);
				System.out.println(survivor);
				// 생존자가 마피아 1명, 시민 1명일 경우
				if (survivor < 3) { 
					resultVote.put("result", 2);
					resultVote.put("userId", userId);
					resultVote.put("userNicknm", userNicknm);
					// [인게임] 승패가 결정남
					delVote(roomNo);
					
					return resultVote;
				} else { // 생존자가 마피아 1명, 시민 n명일 경우
					resultVote.put("result", 0);
					resultVote.put("userId", userId);
					resultVote.put("userNicknm", userNicknm);
					// [인게임] 승패가 결정 안남
					resetVote(roomNo);
					
					return resultVote;
				}
			}
		} else { // 최대 투표자 수가 n명일 경우
			resultVote.put("result", 0);
			// [인게임] 승패가 결정 안남
			resetVote(roomNo);
			
			return resultVote;
		}
	}
	
	// [게임나가기]
	@Transactional
	public void gameOut(MafiaRole mafiaRole) {
		System.out.println("[게임나가기] Service 시작!");
		// [게임나가기] 정보삭제
		delInfo(mafiaRole);
		// [게임나가기] - join_cnt
		joinCntMinus(mafiaRole);
		// [게임나가기] join_cnt 조회
		int getJoinCnt = getJoinCnt(mafiaRole);
		System.out.println("getJoinCnt : " + getJoinCnt);
		// 참여인원이 0이라면
		if (getJoinCnt == 0) {
			// [게임나가기] 방 삭제
			delRoom(mafiaRole);
		}
	}
	
	// [게임시작] mafia_role 조회
	@Override
	public List<RoomUserInfo> getUserInfo(int roomNo) {
		return gameMapper.getUserInfo(roomNo);
	}
	
	// [게임시작] 역할 부여
	@Override
	public void setRole(Map<String, Object> map) {
		gameMapper.setRole(map);
	}
	
	// [게임시작] mafia_vote table 초기세팅
	@Override
	public void initVote(int roomNo) {
		gameMapper.initVote(roomNo);
	}
	
	// [인게임] 최대 투표자 수 조회
	@Override
	public int cntMaxUser(int roomNo) {
		return gameMapper.cntMaxUser(roomNo);
	}
	
	// [인게임] 최대 투표자의 정보
	@Override
	public RoomUserInfo getMaxRole(int roomNo) {
		return gameMapper.getMaxRole(roomNo);
	}

	// [인게임] die
	@Override
	public void setRoleSt(RoomUserInfo roomUserInfo) {
		gameMapper.setRoleSt(roomUserInfo);
	}
	
	// [인게임] 생존자 수 조회
	@Override
	public int cntSurvivor(int roomNo) {
		return gameMapper.cntSurvivor(roomNo);
	}
	
	// [인게임] 승패가 결정남
	@Override
	public void delVote(int roomNo) {
		gameMapper.delVote(roomNo);
	}

	// [인게임] 승패가 결정 안남
	@Override
	public void resetVote(int roomNo) {
		gameMapper.resetVote(roomNo);
	}

	// [게임나가기] 정보삭제
	@Override
	public void delInfo(MafiaRole mafiaRole) {
		gameMapper.delInfo(mafiaRole);
	}

	// [게임나가기] - join_cnt
	@Override
	public void joinCntMinus(MafiaRole mafiaRole) {
		gameMapper.joinCntMinus(mafiaRole);
	}

	// [게임나가기] join_cnt 조회
	@Override
	public int getJoinCnt(MafiaRole mafiaRole) {
		return gameMapper.getJoinCnt(mafiaRole);
	}

	// [게임나가기] 방 삭제
	@Override
	public void delRoom(MafiaRole mafiaRole) {
		gameMapper.delRoom(mafiaRole);
	}
	
}
