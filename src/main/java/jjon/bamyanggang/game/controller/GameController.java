package jjon.bamyanggang.game.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jjon.bamyanggang.game.service.GameService;
import jjon.bamyanggang.model.MafiaRole;
import jjon.bamyanggang.model.RoomUserInfo;

@RestController
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	// [게임시작]
	// MafiaRole room_no 값을 담아 요청(Front-End)
	// List 객체에 방의 입장한 사용자들의 정보 값을 담아 응답 (Back-End)
	@GetMapping("gameStart")
	public ResponseEntity<Map<String, Object>> gameStart(@RequestBody MafiaRole mafiaRole) {
		Map<String, Object> responseBody = new HashMap<String, Object>();
		try {
			List<RoomUserInfo> gameStart = gameService.gameStart(mafiaRole);
			responseBody.put("msg", "[게임시작] 성공!");
			responseBody.put("사용자정보", gameStart);
			
			// 200
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		} catch (Exception e) {
			responseBody.put("msg", "[게임시작] 실패ㅠ");
			
			// 500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}
	}
	
}
