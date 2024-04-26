package jjon.bamyanggang.mafia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jjon.bamyanggang.mafia.mapper.MafiaMapper;
import jjon.bamyanggang.model.MafiaRoom;
import jjon.bamyanggang.model.RoomUserInfo;
import jjon.bamyanggang.model.SetRoom;

@Service
public class MafiaService implements MafiaMapper{

	@Autowired
	private MafiaMapper mafiaMapper;
	
	// [방 생성]
	@Override
	@Transactional
	public void createRoom(SetRoom setRoom) {
		System.out.println("[방 생성] Serivce 시작!");
		// uniqueCode 메소드를 통해 랜덤 코드 값 받기
		String randomCode = uniqueCode(7);
		// 랜덤 코드 확인
		System.out.println("랜덤 코드 : " + randomCode);
		// 중복 검사를 마친 랜덤 코드로 room_cd set
		setRoom.setRoomCd(randomCode);
		try {
			// [방 생성] mafia_room insert
			mafiaMapper.createRoom(setRoom);
			System.out.println("[방 생성] mafia_room insert 성공!");
			// [방 생성] 방 번호 조회
			int getRoomNo = getRoomNo(setRoom.getRoomCd());
			// room_no 확인
			System.out.println("mafia_role 방 번호 값 : " + getRoomNo);
			// mafia_role room_no set
			setRoom.setRoomNo(getRoomNo);
			// [방 생성] mafia_role insert
			initRoom(setRoom);
			System.out.println("[방 생성] mafia_role insert 성공!");
		} catch (Exception e) {
			System.out.println("DB로 전달 실패ㅠ");
			System.out.println(e.getMessage());
		}
	}
	
	// [방 목록]
	@Override
	public List<MafiaRoom> getRoomList() {
		System.out.println("[방 목록] Service 시작!");
		// [방 목록]
		List<MafiaRoom> getRoomList = mafiaMapper.getRoomList();
		return getRoomList;
	}
	
	// [방 입장]
	@Transactional
	public int joinRoom(SetRoom setRoom) {
		System.out.println("[방 입장] Service 시작!");
		// [방 입장] + join_cnt
		joinCntPlus(setRoom);
		System.out.println("[방 입장] ++ join_cnt 성공!");
		// [방 입장] mafia_role insert
		insertUser(setRoom);
		System.out.println("[방 입장] mafia_role insert 성공!");
		// [방 입장] 방 번호 조회
		int getJoinNo = getJoinNo(setRoom);
		System.out.println("[방 입장] 방 번호 조회 성공!");
		
		return getJoinNo;
	}
	
	// [방 대기]
	@Transactional
	public Map<String, Object> standBy(MafiaRoom mafiaRoom) {
		System.out.println("[방 대기] Service 시작!");
		Map<String, Object> standBy = new HashMap<String, Object>();
		// [방 대기] 방 정보 조회
		MafiaRoom getRoomInfo = getRoomInfo(mafiaRoom);
		System.out.println("[방 대기] 방 정보 조회 성공!");
		// [방 대기] 참여 사용자 정보 조회
		List<RoomUserInfo> roomUserInfo = getUserInfo(mafiaRoom);
		System.out.println("[방 대기] 참여 사용자 정보 조회 성공!");
		standBy.put("방 정보", getRoomInfo);
		standBy.put("사용자 정보", roomUserInfo);
		System.out.println("[방 대기] Map 전달 성공!");
		
		return standBy;
	}
	
	// [방 퇴장]
	@Transactional
	public List<MafiaRoom> exitRoom(SetRoom setRoom) {
		System.out.println("[방 퇴장] Service 시작!");
		// [방 목록]
		List<MafiaRoom> getRoomList = getRoomList();
		// [방 퇴장] master 조회 
		int getMaster = getMaster(setRoom);
		System.out.println("[방 퇴장] master 조회 : " + getMaster);
		// [방 퇴장] join_cnt 조회
		int getJoinCnt = getJoinCnt(setRoom);
		System.out.println("[방 퇴장] join_cnt 조회 : " + getJoinCnt);
		// 방장이라면
		if (getMaster == 1) {
			// 참여 인원이 2명 이상이라면
			if (getJoinCnt >= 2) {
				// [방 퇴장] mafia_role delete
				userDel(setRoom);
				// [방 퇴장] - join_cnt
				joinCntMinus(setRoom);
				// [방 퇴장] master update
				masterUpdate(setRoom);
				getRoomList = getRoomList();
				
				return getRoomList;
			} else if (getJoinCnt <= 1) { // 참여 인원이 1명 이하라면
				// [방 퇴장] mafia_role delete
				userDel(setRoom);
				// [방 퇴장] mafia_room delete
				roomDel(setRoom);
				getRoomList = getRoomList();
				
				return getRoomList;			}
		} else { // 방장이 아니라면
			// 참여 인원이 2명 이상이라면 
			if (getJoinCnt >= 2) {
				// [방 퇴장] mafia_role delete
				userDel(setRoom);
				// [방 퇴장] - join_cnt
				joinCntMinus(setRoom);
				getRoomList = getRoomList();
				
				return getRoomList;
			}
		}
		
		return getRoomList;
	}

	// [방 생성] 중복 검사를 마친 랜덤 코드 생성을 위한 메소드
	public String uniqueCode(int length) {
		String randomCode;
		do {
			// createCode 메소드를 통해 randomCode 생성
			randomCode = createCode(length);
			// 중복 코드가 있는 방이 없을 때까지 반복
		} while (isCodeExists(randomCode));
		return randomCode;
	}
	
	// [방 생성] 랜덤 코드 후보 생성을 위한 메소드
	public String createCode(int length) {
		// 랜덤 코드 조합을 위한 문자열
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		// 랜덤 코드 조합을 담을 문자열
		StringBuilder randomCode = new StringBuilder();
		// 랜덤 함수 호출
		Random r = new Random();
		// 매개변수로 받은 length보다 조합된 랜덤 코드의 length가 작을 때까지 반복 (ex : length = 7, 0~6까지 7번 반복)
		while (randomCode.length() < length) {
			// chars의 길이 36, r.nextInt(36) : 0~35의 랜덤한 정수 반환
			int index = r.nextInt(chars.length());
			// charAt : String으로 저장된 문자열 중에서 한 글자만 선택해서 char타입으로 변환
			// randomCode 문자열에 추가
			randomCode.append(chars.charAt(index));
		}
		return randomCode.toString();
	}
	
	// [방 생성] 중복 코드 확인
	public boolean isCodeExists(String randomCode) {
		// 매개변수로 받은 코드를 통해 중복 코드가 있는 방을 조회
		int cntCodeExists = cntCodeExists(randomCode);
		// 조회 값이 0 이상이라면(중복 코드가 있는 방이 있다면) true 값을 전달해 중복 코드 방이 생성되지 않도록 계속 랜덤 코드 생성
		if (cntCodeExists > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// [방 생성] 방 번호 조회
	@Override
	public int getRoomNo(String roomNm) {
		return mafiaMapper.getRoomNo(roomNm);
	}
	
	// [방 생성] mafia_role insert
	@Override
	public void initRoom(SetRoom setRoom) {
		mafiaMapper.initRoom(setRoom);
	}
	
	// [방 생성] 중복 코드 확인
	@Override
	public int cntCodeExists(String randomCode) {
		return mafiaMapper.cntCodeExists(randomCode);
	}
	
	// [방 입장] + join_cnt
	@Override
	public void joinCntPlus(SetRoom setRoom) {
		mafiaMapper.joinCntPlus(setRoom);
	}
	
	// [방 입장] mafia_role insert
	@Override
	public void insertUser(SetRoom setRoom) {
		mafiaMapper.insertUser(setRoom);
	}
	
	// [방 입장] 방 번호 조회
	@Override
	public int getJoinNo(SetRoom setRoom) {
		return mafiaMapper.getJoinNo(setRoom);
	}
	
	// [방 대기] 방 정보 조회
	@Override
	public MafiaRoom getRoomInfo(MafiaRoom mafiaRoom) {
		return mafiaMapper.getRoomInfo(mafiaRoom);
	}
	
	// [방 대기] 참여 사용자 정보 조회
	@Override
	public List<RoomUserInfo> getUserInfo(MafiaRoom mafiaRoom) {
		return mafiaMapper.getUserInfo(mafiaRoom);
	}
	
	// [방 퇴장] master 조회
	@Override
	public Integer getMaster(SetRoom setRoom) {
		return mafiaMapper.getMaster(setRoom);
	}
	
	// [방 퇴장] join_cnt 조회
	@Override
	public int getJoinCnt(SetRoom setRoom) {
		return mafiaMapper.getJoinCnt(setRoom);
	}

	// [방 퇴장] mafia_role delete
	@Override
	public void userDel(SetRoom setRoom) {
		mafiaMapper.userDel(setRoom);
	}
	

	// [방 퇴장] - join_cnt
	@Override
	public void joinCntMinus(SetRoom setRoom) {
		mafiaMapper.joinCntMinus(setRoom);
	}
	
	// [방 퇴장] master update
	@Override
	public void masterUpdate(SetRoom setRoom) {
		mafiaMapper.masterUpdate(setRoom);
	}
	
	// [방 퇴장] mafia_room delete
	@Override
	public void roomDel(SetRoom setRoom) {
		mafiaMapper.roomDel(setRoom);
	}
	
}
