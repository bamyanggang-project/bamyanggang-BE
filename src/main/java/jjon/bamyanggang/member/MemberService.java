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

	
	public MemberService(MemberMapper memberMapper, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder) {
		
		this.memberMapper = memberMapper;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.passwordEncoder = passwordEncoder;
	}
	
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
			            isPhoneNumAvailable(memberDto.getPhoneNum1(), memberDto.getPhoneNum2(), memberDto.getPhoneNum3()) > 0
			            
					 ) {
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
	        String hashPassWd = bCryptPasswordEncoder.encode(memberDto.getPassWd());
	        memberDto.setPassWd(hashPassWd);
	        memberMapper.updateMember(memberDto);
	        return true;
	    } catch (Exception e) {
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
                return null;
            }
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
    }

    public byte[] getImageBytes(String imagePath) {
    	
    	if (imagePath == null || imagePath.isEmpty()) {
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
	

	

