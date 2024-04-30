package jjon.bamyanggang.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jjon.bamyanggang.community.service.CommunityServiceImpl;
import jjon.bamyanggang.model.CommunityDto;


@RestController
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private CommunityServiceImpl communityService;
	
	//자게 글쓰기(로그인확인)
	@PostMapping("/communitywrite")
	public ResponseEntity<Integer> communitywrite(@RequestBody CommunityDto community)throws Exception{
		System.out.println("컨트롤러 들어옴");
				
		int result = communityService.communityInsert(community);//insert되면 result==1 리턴
		
		return new ResponseEntity<>(result, HttpStatus.OK);
		//성공하면 1리턴
	}
	
	//자게 글 목록
	@GetMapping("/communitylist")
	public ResponseEntity<List<CommunityDto>> communitylist()throws Exception{
		
		List<CommunityDto> communitylist = communityService.getCommunityList();
		return new ResponseEntity<>(communitylist, HttpStatus.OK);
	}
	
	
	
	//자게 글 상세보기 
	@GetMapping("/communitycontent/{postNo}")
	public ResponseEntity<CommunityDto>communitycontent(@PathVariable("postNo") int postNo)throws Exception{
		
		communityService.updateVw(postNo); //조회수 1증가
		CommunityDto community = communityService.getCommunity(postNo);// 상세내용 구하기
	
		return new ResponseEntity<>(community,HttpStatus.OK);
	}
	
	
	//자게 글 수정(로그인확인) 
	@PostMapping("/communityupdate/{postNo}")
	public ResponseEntity<Integer> communityupdate(@RequestBody CommunityDto community)throws Exception {
		System.out.println("community:"+ community);
		
		int result = communityService.communityUpdate(community);	
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	//자게 글 삭제(로그인확인)
	@DeleteMapping("/communitydelete/{postNo}")
	public ResponseEntity<Integer> communitydelete(@RequestBody CommunityDto community)throws Exception{
		System.out.println("삭제 들어옴");
		int result = communityService.communityDelete(community.getPostNo());
		return new ResponseEntity<>(result,HttpStatus.OK);
		//1 리턴 성공 , 0은 실패?
	}
	
}
