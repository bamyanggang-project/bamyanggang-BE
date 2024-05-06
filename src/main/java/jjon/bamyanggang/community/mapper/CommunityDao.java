package jjon.bamyanggang.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jjon.bamyanggang.model.CommunityDto;

@Mapper
public interface CommunityDao {
		
	//게시판 저장
	public int communityInsert(CommunityDto community) throws Exception;
	
	//게시판 목록
	public List<CommunityDto> getCommunityList()throws Exception;

	//상세페이지
	public CommunityDto getCommunity(int postNo)throws Exception;
	
	//조회수 증가
	public void updateVw(int postNo)throws Exception;

	//게시판 수정
	public int communityUpdate(CommunityDto community)throws Exception;

	//게시글 삭제
	public int communityDelete(int postNo)throws Exception;




	
	
}
