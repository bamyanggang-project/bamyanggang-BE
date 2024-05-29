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
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<String> addMember(@RequestBody MemberDto memberDto) {
        boolean success = memberService.addMember(memberDto);
        if (success) {
            return ResponseEntity.ok("회원가입이 성공적으로 처리되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("회원가입 실패, ID, 닉네임, 이메일, 휴대폰번호 중복검사 해주세요.");
        }
    }

    @PostMapping("/members/image")
    public ResponseEntity<String> addMemberImage(@RequestPart("profileImage") MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            return ResponseEntity.badRequest().body("이미지 파일이 비어있습니다.");
        }
        String imagePath = memberService.saveImage(profileImage);
        if (imagePath != null) {
            return ResponseEntity.ok("프로필 이미지가 성공적으로 업로드되었습니다.");
        } else {
            return ResponseEntity.status(500).body("이미지 파일 저장에 실패했습니다.");
        }
    }

    @PutMapping("/members/{userId}")
    public ResponseEntity<String> updateMember(@PathVariable("userId") String userId, @RequestBody MemberDto memberDto) {
        memberDto.setUserId(userId);
        boolean success = memberService.updateMember(memberDto);
        if (success) {
            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("회원 정보 수정에 실패하였습니다.");
        }
    }

    @DeleteMapping("/members")
    public ResponseEntity<String> deleteMember(@RequestBody MemberDto memberDto) {
        boolean success = memberService.deleteMember(memberDto);
        if (success) {
            return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
        }
        return ResponseEntity.badRequest().body("회원 탈퇴에 실패하였습니다.");
    }

    @GetMapping("/members/{userId}")
    public ResponseEntity<MemberDto> getUserByUserId(@PathVariable("userId") String userId) {
        MemberDto memberDto = memberService.getUserByUserId(userId);
        if (memberDto != null) {
            return ResponseEntity.ok(memberDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/members/check/id/{userId}")
    public ResponseEntity<Boolean> checkIdAvailability(@PathVariable("userId") String userId) {
        boolean available = memberService.isIdAvailable(userId);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/members/check/nickname/{nickName}")
    public ResponseEntity<Boolean> checkNickAvailability(@PathVariable("nickName") String nickName) {
        boolean available = memberService.isNickNameAvailable(nickName);
        return ResponseEntity.ok(available);
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
