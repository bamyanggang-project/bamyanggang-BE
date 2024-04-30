package jjon.bamyanggang.mafia.service;

import java.util.List;
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
		System.out.println("randdomCode : " + randomCode);
		// 중복 검사를 마친 랜덤 코드로 room_cd set
		setRoom.setRoomCd(randomCode);
		try {
			// [방 생성] mafia_room table 세팅
			mafiaMapper.createRoom(setRoom);
			// [방 생성] 방 번호 조회
			int getRoomNo = getRoomNo(setRoom.getRoomCd());
			System.out.println("getRoomNo : " + getRoomNo);
			// mafia_role room_no set
			setRoom.setRoomNo(getRoomNo);
			// [방 생성] mafia_role table 초기세팅
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
		return mafiaMapper.getRoomList();
	}
	
	// [방 입장]
	@Transactional
	public int joinRoom(SetRoom setRoom) {
		System.out.println("[방 입장] Service 시작!");
		// [방 입장] + join_cnt
		joinCntPlus(setRoom);
		// [방 입장] mafia_role insert
		insertUser(setRoom);
		// [방 입장] 방 번호 조회
		int getJoinNo = getJoinNo(setRoom);
		System.out.println("getJoinNo : " + getJoinNo);
		
		return getJoinNo;
	}
	
	// [방 대기] 방 정보 조회
	@Override
	public MafiaRoom getRoomInfo(int roomNo) {
		System.out.println("[방 대기] - 방 정보 조회 Service 시작!");
		return mafiaMapper.getRoomInfo(roomNo);
	}
	
	// [방 대기] 참여 사용자 정보 조회
	@Override
	public List<RoomUserInfo> getUserInfo(int roomNo) {
		System.out.println("[방 대기] - 참여 사용자 정보 조회 Service 시작!");
		return mafiaMapper.getUserInfo(roomNo);
	}
	
	// [방 퇴장]
	@Transactional
	public void exitRoom(SetRoom setRoom) {
		System.out.println("[방 퇴장] Service 시작!");
		// [방 퇴장] master 조회 
		int getMaster = getMaster(setRoom);
		System.out.println("getMaster : " + getMaster);
		// [방 퇴장] join_cnt 조회
		int getJoinCnt = getJoinCnt(setRoom);
		System.out.println("getJoinCnt : " + getJoinCnt);
		// 방장이라면
		if (getMaster == 1) {
			// 참여 인원이 2명 이상이라면
			if (getJoinCnt >= 2) {
				// [방 퇴장] mafia_role delete
				delUser(setRoom);
				// [방 퇴장] - join_cnt
				joinCntMinus(setRoom);
				// [방 퇴장] master update
				masterUdt(setRoom);
				
			} else if (getJoinCnt == 1) { // 참여 인원이 1명 이하라면
				// [방 퇴장] mafia_role delete
				delUser(setRoom);
				// [방 퇴장] mafia_room delete
				delRoom(setRoom);
				
			}
		} else { // 방장이 아니라면
			// 참여 인원이 2명 이상이라면 
			if (getJoinCnt >= 2) {
				// [방 퇴장] mafia_role delete
				delUser(setRoom);
				// [방 퇴장] - join_cnt
				joinCntMinus(setRoom);
			}
		}
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
		Random rnd = new Random();
		// 매개변수로 받은 length보다 조합된 랜덤 코드의 length가 작을 때까지 반복 (ex : length = 7, 0~6까지 7번 반복)
		while (randomCode.length() < length) {
			// chars의 길이 36, r.nextInt(36) : 0~35의 랜덤한 정수 반환
			int index = rnd.nextInt(chars.length());
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

	// [방 생성] mafia_role table 초기세팅
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
	public void delUser(SetRoom setRoom) {
		mafiaMapper.delUser(setRoom);
	}

	// [방 퇴장] - join_cnt
	@Override
	public void joinCntMinus(SetRoom setRoom) {
		mafiaMapper.joinCntMinus(setRoom);
	}
	
	// [방 퇴장] master update
	@Override
	public void masterUdt(SetRoom setRoom) {
		mafiaMapper.masterUdt(setRoom);
	}
	
	// [방 퇴장] mafia_room delete
	@Override
	public void delRoom(SetRoom setRoom) {
		mafiaMapper.delRoom(setRoom);
	}
	
}
