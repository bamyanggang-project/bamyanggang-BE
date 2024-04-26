package jjon.bamyanggang.mafia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jjon.bamyanggang.mafia.service.MafiaService;
import jjon.bamyanggang.model.MafiaRoom;
import jjon.bamyanggang.model.SetRoom;

@RestController
public class MafiaController {
	
	@Autowired
	private MafiaService mafiaService;
	
	// [방 생성]
	// SetRoom 객체에 user_id, room_nm, room_st, room_pw 값을 담아 요청 (Front-End)
	@PostMapping("createRoom")
	public ResponseEntity<Map<String,Object>> createRoom(@RequestBody SetRoom setRoom) {
		System.out.println("[방 생성] Controller 시작!");
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			mafiaService.createRoom(setRoom);
			int getRoomNo = mafiaService.getRoomNo(setRoom.getRoomCd());
			responseBody.put("msg", "[방 생성] 성공!");
			responseBody.put("roomNo", getRoomNo);
			
			// 201
			return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[방 생성] 실패ㅠ");
			
			// 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}
	}
	
	// [방 목록]
	// List 객체에 방 목록 값을 담아 응답 (Back-End)
	@GetMapping("getRoomList")
	public ResponseEntity<Map<String, Object>> getRoomList() {
		System.out.println("[방 목록] Controller 시작!");
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			List<MafiaRoom> getRoomList = mafiaService.getRoomList();
			responseBody.put("msg", "[방 목록] 성공!");
			responseBody.put("방 목록", getRoomList);
			
			// 200
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[방 목록] 실패ㅠ");
			
			// 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}
	}
	
	// [방 입장]
	// SetRoom 객체에 user_id, room_no 값을 담아 요청 (Front-End)
	// int형 room_no 값을 응답 (Back-End)
	@PostMapping("joinRoom")
	public ResponseEntity<Map<String, Object>> joinRoom(@RequestBody SetRoom setRoom) {
		System.out.println("[방 입장] Controller 시작!");
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			int getJoinNo = mafiaService.joinRoom(setRoom);
			responseBody.put("msg", "[방 입장] 완료!");
			responseBody.put("roomNo", getJoinNo);
			
			// 200
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[방 입장] 실패ㅠ");
			
			// 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}
	}
	
	// [방 대기]
	// MafiaRoom 객체에 room_no 값을 담아 요청 (Front-End)
	// Map 객체에 대기 방의 정보 값, 대기 방의 입장한 사용자들의 정보 값을 담아 응답 (Back-End)
	@GetMapping("standBy") 
	public ResponseEntity<Map<String, Object>> standBy(@RequestBody MafiaRoom mafiaRoom) {
		System.out.println("[방 대기] Controller 시작!");
		Map<String, Object> responseBody =new HashMap<String, Object>();
		try {
			Map<String, Object> standBy = mafiaService.standBy(mafiaRoom);
			responseBody.put("msg", "[방 대기] 성공!");
			responseBody.put("방 대기 정보", standBy);
			
			// 200
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[방 대기] 실패ㅠ");
			
			// 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}
	}
	
	// [방 퇴장]
	// Setroom 객체에 user_id, room_no 값을 담아 요청 (Front-Ent)
	// List 객체에 방 목록 값을 담아 응답
	@PostMapping("exitRoom")
	public ResponseEntity<Map<String, Object>> exitRoom(@RequestBody SetRoom setRoom) {
		System.out.println("[방 퇴장] Controller 시작!");
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			List<MafiaRoom> getRoomList = mafiaService.exitRoom(setRoom);
			responseBody.put("msg", "[방 퇴장] 성공!");
			responseBody.put("방 목록", getRoomList);
			
			// 200
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[방 퇴장] 실패ㅠ");
			
			// 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}
	}
 
}
