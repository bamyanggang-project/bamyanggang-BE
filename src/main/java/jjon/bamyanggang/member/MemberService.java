package jjon.bamyanggang.member;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.directory}")
    private String uploadDirectory;
	// 이미지파일 경로 저장할 위치 지정 (yml) 

    public MemberService(MemberMapper memberMapper, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    public int isIdAvailable(String userId) {
        return memberMapper.isMemberExistsById(userId);
    }

    public int isEmailAvailable(String emailNum1) {
        return memberMapper.isMemberExistsByEmail(emailNum1);
    }

    public int isNickNameAvailable(String nickName) {
        return memberMapper.isMemberExistsByNickName(nickName);
    }

    public int isPhoneNumAvailable(String phoneNum1, String phoneNum2, String phoneNum3) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneNum1", phoneNum1);
        params.put("phoneNum2", phoneNum2);
        params.put("phoneNum3", phoneNum3);
        return memberMapper.isMemberExistsByPhoneNumber(params);
    }

    public boolean addMember(MemberDto memberDto) {
        if (isIdAvailable(memberDto.getUserId()) > 0 ||
            isEmailAvailable(memberDto.getEmailNum1()) > 0 ||
            isNickNameAvailable(memberDto.getNickName()) > 0 ||
            isPhoneNumAvailable(memberDto.getPhoneNum1(), memberDto.getPhoneNum2(), memberDto.getPhoneNum3()) > 0) {
            return false;
        }
	    // 하나라도 중복있을시 회원가입 불가

        String hashPassWd = bCryptPasswordEncoder.encode(memberDto.getPassWd());
        memberDto.setPassWd(hashPassWd);
        memberMapper.addMember(memberDto);
        return true;
    }

	
	// 비밀번호 bCrypt 로 다시 한번 인코딩	
    public MemberDto getUserByUserId(String userId) {
        return memberMapper.getUserByUserId(userId);
    }

    public boolean updateMember(MemberDto memberDto) {
        try {
            String hashPassWd = bCryptPasswordEncoder.encode(memberDto.getPassWd());
            memberDto.setPassWd(hashPassWd);
            memberMapper.updateMember(memberDto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	// 회원수정도 비밀번호 변경시 bCrypt 로 인코딩하여 DB저장

    public boolean deleteMember(MemberDto memberDto) {
        String userId = memberDto.getUserId();
        String inputPw = memberDto.getPassWd();
        String storedPw = memberMapper.findByPassword(userId);

        if (userId != null && passwordEncoder.matches(inputPw, storedPw)) {
            memberMapper.deleteMember(memberDto);
            return true;
        }
        return false;
    }
	// 회원탈퇴시 비밀번호 확인 후에 진행 (비밀번호는 디코딩 후 일치한지 확인후에 진행)

    public String saveImage(MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            return null;
        }
        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = profileImage.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            profileImage.transferTo(filePath.toFile());
            return filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	    // 회원가입 프로필 이미지 저장 
	    // 경로는 DB에 저장하며 사진파일은 로컬파일에 저장합니다 ( 배포시 배포서버에 따로 저장)
    }
}
	

	

