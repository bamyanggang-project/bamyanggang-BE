package jjon.bamyanggang.login.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jjon.bamyanggang.login.service.UserService;


@RestController
public class LoginController {
	
	private final UserService userService;
	
	public LoginController(UserService userService) {
		
		this.userService = userService;
	}

	
	@PostMapping("/")
    public String login() {
		
		
        return "로그인 되었습니다.";
    }

	
	@DeleteMapping("/delete/{userId}")
	public String DeleteMember(@PathVariable("userId") String userId)
	{
		userService.deleteByUsername(userId);
		
		return "사용자 " + userId + "이 (가) 성공적으로 삭제 되었습니다. ";
		
	}
}
