package jjon.bamyanggang.member;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	
	private String userId;
	private String passWd;
	private String userName;
	private String nickName;
	private String profileImagePath;
	private String phoneNum1;
	private String phoneNum2;
	private String phoneNum3;	
	private String emailNum1;
	private String emailNum2;
	private String birth;
	private String gender;
	
	private String role = "user";
	private int withdrawal = 1; // 임의설정




	
	
	

}
