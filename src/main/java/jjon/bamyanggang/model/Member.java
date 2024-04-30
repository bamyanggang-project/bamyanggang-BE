package jjon.bamyanggang.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("member")
public class Member {
	
	private int id;
	private String userAuthority;
	private String userId;
	private String userPw;
	private String userNm;
	private String userNicknm;
	private String userImg;
	private String userTel1;
	private String userTel2;
	private String userTel3;
	private String userEmail1;
	private String userEmail2;
	private String userBirth;
	private String userGender;
	private String userRegister;
	private int userWithdrawal;
	
}
