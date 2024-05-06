package jjon.bamyanggang.member;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MemberService {

	private final MemberMapper memberMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PasswordEncoder passwordEncoder;
    @Value("${upload.directory}")
    private String uploadDirectory;

	
	public MemberService(MemberMapper memberMapper, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder) {
		
		this.memberMapper = memberMapper;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.passwordEncoder = passwordEncoder;
	}
	
	// 중복 확인 메서드 추가
	 public int isIdAvailable(String userId) {
	        
		 
		int result =  memberMapper.isMemberExistsById(userId);
		return result;
		  
	 }
	

    public int isEmailAvailable(String emailNum1) {
    	int result =  memberMapper.isMemberExistsByEmail(emailNum1);
		return result;
    }

    public int isNickNameAvailable(String nickName) {
    	int result =  memberMapper.isMemberExistsByNickName(nickName);
		return result;
    }

    public int isPhoneNumAvailable(String phoneNum1, String phoneNum2, String phoneNum3) {
        // getMemberByPhoneNumber 메서드의 반환값이 null이면 사용 가능한 핸드폰 번호이고,
        // null이 아니면 이미 사용 중인 핸드폰 번호입니다.
    	int result = memberMapper.isMemberExistsByPhoneNumber(phoneNum1, phoneNum2, phoneNum3);
    	System.out.println(result);
    	return result;
    }
	
	public boolean addMember(MemberDto memberDto) {
		
		
		        // 이미 등록된 아이디인지 확인
			 if (isIdAvailable(memberDto.getUserId()) > 0 ||
			            isEmailAvailable(memberDto.getEmailNum1()) > 0 ||
			            isNickNameAvailable(memberDto.getNickName()) > 0 ||
			            isPhoneNumAvailable(memberDto.getPhoneNum1(), memberDto.getPhoneNum2(), memberDto.getPhoneNum3()) > 0
			            
					 ) {
			            // 이미 등록된 정보가 하나라도 있으면 회원 추가 실패
			            return false;
			        }
		        else {
		        String hashPassWd = bCryptPasswordEncoder.encode(memberDto.getPassWd());
		        memberDto.setPassWd(hashPassWd);
		        memberMapper.addMember(memberDto);
		        return true;
		        }
		    } 
	
	public MemberDto getUserByUserId(String userId) {
	        
		
	        return memberMapper.getUserByUserId(userId);
	        
	    }
	

	
	
	public boolean updateMember(MemberDto memberDto) {
		
		
			
		try {
	        // 클라이언트로부터 전달된 memberDto에는 업데이트할 회원의 정보가 포함되어 있습니다.
	        // 따라서 memberDto를 그대로 매퍼의 updateMember 메서드에 전달하여 업데이트를 수행합니다.
	        memberMapper.updateMember(memberDto);
	        return true;
	    } catch (Exception e) {
	        // 업데이트에 실패한 경우에 대한 처리
	        return false;
	    }
	}
	
	public boolean deleteMember(MemberDto memberDto) {
			
			String userId = memberDto.getUserId();
			String inputPw = memberDto.getPassWd();
			String storedPw = memberMapper.findByPassword(userId);
			
		 	if(userId != null && passwordEncoder.matches(inputPw, storedPw))
		 	{
			memberMapper.deleteMember(memberDto);
			return true;
		 	}
		 	else {
		 		return false;
		 	}
		
		
	}
	
	
		
	



	public String saveImage(MultipartFile profileImage) {
        try {
        	
        	if (profileImage == null || profileImage.isEmpty()) {
                // 이미지가 전송되지 않았을 때의 처리
                return null;
            }
            // 파일을 저장할 경로 설정
            Path uploadPath = Paths.get(uploadDirectory);
            // 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // 파일명 생성
            String fileName = profileImage.getOriginalFilename();
            // 파일 저장 경로 설정
            Path filePath = uploadPath.resolve(fileName);
            // 파일 저장
            profileImage.transferTo(filePath.toFile());
            // 저장된 파일의 경로를 반환
            return filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getImageBytes(String imagePath) {
    	
    	if (imagePath == null || imagePath.isEmpty()) {
            // 이미지가 전송되지 않았을 때의 처리
            return null;
        }
        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
	

	

