package jjon.bamyanggang.login.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jjon.bamyanggang.login.service.ReissueService;


@RestController
public class ReissueController {
	
	private final ReissueService reissueService;
	// 생성자 방식 주입.
	public ReissueController ( ReissueService reissueService) {
		
		this.reissueService = reissueService;
	}
	
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
		        return reissueService.reissue(request, response);
		    }
	}
	
	


