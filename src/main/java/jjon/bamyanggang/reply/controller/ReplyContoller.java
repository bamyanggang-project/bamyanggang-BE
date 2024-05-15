package jjon.bamyanggang.reply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jjon.bamyanggang.community.service.CommunityService;
import jjon.bamyanggang.model.CommunityDto;
import jjon.bamyanggang.model.ReplyDto;
import jjon.bamyanggang.reply.service.ReplyService;

@RestController
@RequestMapping("/reply")
public class ReplyContoller {

	@Autowired
	private ReplyService replyService;

	@Autowired
	private CommunityService communityService;

	// 댓글작성 (로그인 확인)
	@PostMapping("/replywrite")
	public ResponseEntity<Integer> replywrite(@RequestBody ReplyDto reply) throws Exception {
		System.out.println("댓글입력컨트롤러");
		int postNo = reply.getPostNo(); // 이부분 추가됨
		int result = replyService.replyInsert(reply);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	
	// 댓글목록
	@GetMapping("/replylist/{postNo}")
	public ResponseEntity<Map<String, Object>> getReplyList(@PathVariable("postNo") int postNo) throws Exception {
		System.out.println("postNo :" + postNo);

		CommunityDto community = communityService.getCommunity(postNo);
		System.out.println("community :" + community);
		List<ReplyDto> replylist = replyService.getReplyList(postNo);
		System.out.println("replylist :" + replylist);

		Map map = new HashMap<>();
		map.put("community", community);
		map.put("replylist", replylist);

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	// 댓글수정(로그인 확인, id 확인)
	@PostMapping("/replyupdate/{postNo}/{replyNo}")
	public ResponseEntity<Integer> replyupdate(@RequestBody ReplyDto reply) throws Exception {
		System.out.println("reply : " + reply);

		int result = replyService.replyUpdate(reply);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	//댓글삭제(로그인 확인, id 확인)
	@DeleteMapping("/replydelete/{replyNo}")
	public ResponseEntity<Integer> replydelete(@PathVariable("replyNo") int replyNo)throws Exception{
		
		 System.out.println("replyNo : "+replyNo);
		 int result = replyService.replyDelete(replyNo);
		 return new ResponseEntity<>(result,HttpStatus.OK);
	}
	 

}
