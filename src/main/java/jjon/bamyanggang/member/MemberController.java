package jjon.bamyanggang.member;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class MemberController {
	
	 package jjon.bamyanggang.member;


		  @DeleteMapping("/api/deletemember")
		  public ResponseEntity<String> deleteMember(@RequestBody MemberDto memberDto) {
			  
			  boolean succes = memberService.deleteMember(memberDto);
			  if(succes) {
				  return ResponseEntity.ok("회원 탈퇴원료");
			  }
			  	return ResponseEntity.badRequest().body("회원 탈퇴 실패");
			  
		  }
		  
			  @GetMapping("/api/userInfo/{userId}")
			    public MemberDto getUserByUserId(@PathVariable("userId") String userId) {
			      MemberDto memberDto =  memberService.getUserByUserId(userId);
			      
			      return  memberDto;
			      
			    }
	  
	
      @GetMapping("/api/checkIdAvailability/idCheck/{userId}")
      public Integer checkIdAvailability(@PathVariable("userId") String userId) {
    	  int availability = memberService.isIdAvailable(userId);
    	    return availability;
    	    
      }

    @GetMapping("/members/check/email/{emailNum1}")
    public ResponseEntity<Boolean> checkEmailAvailability(@PathVariable("emailNum1") String emailNum1) {
        boolean available = memberService.isEmailAvailable(emailNum1);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/members/check/phone")
    public ResponseEntity<Boolean> checkPhoneAvailability(@RequestParam String phoneNum1, @RequestParam String phoneNum2, @RequestParam String phoneNum3) {
        boolean available = memberService.isPhoneNumAvailable(phoneNum1, phoneNum2, phoneNum3);
        return ResponseEntity.ok(available);
    }
}
