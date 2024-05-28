package jjon.bamyanggang.member;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
		
		public int addMember(MemberDto memberDto);
		public MemberDto getUserByUserId(String userId);
		public void updateMember(MemberDto memberDto);
		public void deleteMember(MemberDto memberDto);
		public String findByPassword(String userId);
		public int isMemberExistsById(String userId);
	    	public int isMemberExistsByEmail(String emailnum1);
	    	public int isMemberExistsByNickName(String nickName);
		public int isMemberExistsByPhoneNumber(Map<String, String> params);
	    
}
