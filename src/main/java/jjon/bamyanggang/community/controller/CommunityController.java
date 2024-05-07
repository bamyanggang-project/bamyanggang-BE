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
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private ReplyService replyService;
	
	//자게 글쓰기
	@PostMapping("/communitywrite")					
	public ResponseEntity<Integer> communitywrite(@RequestBody CommunityDto community
												  //@RequestPart(value = "imgfile", required = false) MultipartFile file
												  )throws Exception{
												//		input의 name값이랑 일치시키기
		
//		String filename = file.getOriginalFilename(); 
//		int size = (int)file.getSize(); //첨부파일 크기 (Byte)
//		
//		//파일 저장 디렉토리 경로 :: 어떻게 잡을지 협의
//	    String path = "C:/upload"; 
//	    
//		//확인
//	    System.out.println("file : "+file);
//	    System.out.println("filename : "+filename);
//	    System.out.println("size :" + size);
//		System.out.println("Path :" + path);
//	    
		int result = 0;
//		String newfilename="";
//		
//		if(file != null && !file.isEmpty()) { //첨부파일 전송된 경우
//			
//			//파일중복문제
//			String extension = filename.substring(filename.lastIndexOf("."), filename.length()); //확장자 파싱
//			System.out.println("extension:"+extension);  // extension: 확장자
//			
//			UUID uuid = UUID.randomUUID();
//			
//			newfilename = uuid.toString() + extension;
//			System.out.println("newfilename:"+newfilename);
//			
//			if(size > 100000){ //100KB (큰건가.. 작은것같은디)
//				result = 2;
//				return new ResponseEntity<>(result, HttpStatus.OK);
//				
//				//아래의 확장자명이 아닌 경우(이미지x) 
//			}else if(!extension.equals(".jpg")  &&
//					 !extension.equals(".jpeg") &&
//					 !extension.equals(".gif")  &&
//					 !extension.equals(".png") 
//					){
//				result = 3;
//				return new ResponseEntity<>(result, HttpStatus.OK);
//			}
//			//해당경로에 파일 저장
//			file.transferTo(new File(path + "/" + newfilename));
//		}
//		
//
//		
//			community.setImg(newfilename);
			result = communityService.communityInsert(community);//insert되면 result==1 리턴
			
			return new ResponseEntity<>(result, HttpStatus.OK);//성공하면 1리턴
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
	public ResponseEntity<Integer> communityupdate(@RequestBody CommunityDto community,
													@PathVariable("postNo") int postNo
//													@RequestPart(value = "imgfile", required = false) MultipartFile file
												   )throws Exception {
//		System.out.println("수정컨트롤러");
//		String filename = null;
//		int size = 0;
//		String newfilename="";
		int result=0;
		
//		if(file != null && !file.isEmpty()) { //첨부파일 전송된 경우
//			
//			filename = file.getOriginalFilename();
//			size =(int) file.getSize();
//			
//			String path = "C:/upload";//파일 저장경로
//			
//			
//			//중복문제 해결
//			String extension = filename.substring(filename.lastIndexOf("."), filename.length());
//			System.out.println(extension);
//			
//			UUID uuid = UUID.randomUUID();
//			newfilename = uuid.toString() + extension;
//			
//			System.out.println("newfilename : "+ newfilename);
//			
//			if(size > 100000) { //100kb
//				result = 2;
//				return new ResponseEntity<>(result,HttpStatus.OK);
//				
//			}else if(!extension.equals(".jpg") &&
//					 !extension.equals(".jpeg") &&
//					 !extension.equals(".gif") &&
//					 !extension.equals(".png")) {
//				
//				result	=3;
//				return new ResponseEntity<>(result,HttpStatus.OK);
//			}
//			//파일 저장
//			file.transferTo(new File(path+"/"+newfilename));
//			
//		}
//		
//		if(newfilename.isEmpty()) { //첨부파일 수정 x
//			//기존파일명 가져오기
//			CommunityDto existingCommunity = communityService.getCommunity(community.getPostNo());
//			newfilename = existingCommunity.getImg();
//		}
//		
//		community.setImg(newfilename);
		community.setPostNo(postNo);
		result = communityService.communityUpdate(community);	
		System.out.println("community:"+ community);
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	//자게 글 삭제(로그인확인)
	@DeleteMapping("/communitydelete/{postNo}")
	public ResponseEntity<Integer> communitydelete(@PathVariable("postNo") int postNo)throws Exception{
		System.out.println("삭제 들어옴");
		int result = communityService.communityDelete(postNo);
		return new ResponseEntity<>(result,HttpStatus.OK);
		//1 리턴 성공 , 0은 실패?
	}
	
}
