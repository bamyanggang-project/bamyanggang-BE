package jjon.bamyanggang.community.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jjon.bamyanggang.community.service.CommunityService;
import jjon.bamyanggang.model.CommunityDto;
import jjon.bamyanggang.reply.service.ReplyService;


@RestController
@RequestMapping("/api/community")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	
	@PostMapping("/communitywrite")					
	public ResponseEntity<Integer> communitywrite(@RequestBody CommunityDto community
												  )throws Exception{
	    
		int result = 0;
		result = communityService.communityInsert(community);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/communitylist")
	public ResponseEntity<List<CommunityDto>> communitylist()throws Exception{
		
		List<CommunityDto> communitylist = communityService.getCommunityList();
		return new ResponseEntity<>(communitylist, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/communitycontent/{postNo}")
	public ResponseEntity<CommunityDto>communitycontent(@PathVariable("postNo") int postNo)throws Exception{
		
		communityService.updateVw(postNo); 
		CommunityDto community = communityService.getCommunity(postNo);
	
		
		int prevPostNo = communityService.getPrevPostNo(postNo);
		if(prevPostNo == -1) {
			community.setPrevPostNo(0);
		}else {
			community.setPrevPostNo(prevPostNo);
		}


	
		int nextPostNo = communityService.getNextPostNo(postNo);
	
		if(nextPostNo == -1 ) {
			community.setNextPostNo(0);
		}else {
			community.setNextPostNo(nextPostNo);
		}
		
		return new ResponseEntity<>(community,HttpStatus.OK);
	}
	
	
	@PostMapping("/communityupdate/{postNo}")
	public ResponseEntity<Integer> communityupdate(@RequestBody CommunityDto community,
													@PathVariable("postNo") int postNo
												   )throws Exception {
		int result=0;
		
		community.setPostNo(postNo);
		result = communityService.communityUpdate(community);	
		System.out.println("community:"+ community);
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@DeleteMapping("/communitydelete/{postNo}")
	public ResponseEntity<Integer> communitydelete(@PathVariable("postNo") int postNo)throws Exception{
		System.out.println("삭제 들어옴");
		int result = communityService.communityDelete(postNo);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	
	
	
}
