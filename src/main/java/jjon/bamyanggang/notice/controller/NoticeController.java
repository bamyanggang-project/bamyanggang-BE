package jjon.bamyanggang.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jjon.bamyanggang.model.NoticeDto;
import jjon.bamyanggang.notice.service.NoticeService;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	//공지사항 목록
	@GetMapping("/noticelist")
	public ResponseEntity<Map<String, Object>> noticelist(
			@RequestParam(value="page", defaultValue = "1") int page,
			@RequestParam(value="size" ,defaultValue = "10") int size){
		
		int start = (page - 1) * size;
		
		Map m = new HashMap<>();
		m.put("start", start);
		m.put("size", size);
		
		
		List<NoticeDto> noticeList = noticeService.getNoticeList(m);
		System.out.println("페이지 : "+ page +" 글목록 :" + noticeList);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("notices", noticeList);
		map.put("page", page);
		map.put("size", size);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	

	//상세페이지(조회수 1증가 + 상세내용)
	@GetMapping("/noticecontent/{postNo}")
	public ResponseEntity<Map<String, Object>> noticecontent(@PathVariable("postNo") int postNo){
		
		noticeService.updateVw(postNo); 
		NoticeDto notice = noticeService.getNotice(postNo); 
		
		System.out.println("postNo : "+ postNo);
		
		int prevPostNo = noticeService.getPrevPostNo(postNo);
		if(prevPostNo == -1) {
			notice.setPrevPostNo(0);
		}else {
			notice.setPrevPostNo(prevPostNo);
		}
		
		int nextPostNo = noticeService.getNextPostNo(postNo);
		if(nextPostNo == -1) {
			notice.setNextPostNo(0);
		}else {
			notice.setNextPostNo(nextPostNo);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("notice", notice);
		
		
		return new ResponseEntity<>(map, HttpStatus.OK);
		
	}

	
}
