package jjon.bamyanggang.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jjon.bamyanggang.community.mapper.CommunityDao;
import jjon.bamyanggang.model.CommunityDto;

@Service
public class CommunityService implements CommunityDao {
	
	@Autowired
	private	CommunityDao CommunityDao;

	//자유게시판 작성
	public int communityInsert(CommunityDto community) throws Exception {
		return CommunityDao.communityInsert(community);
	}

	//자유게시판 목록
	public List<CommunityDto> getCommunityList() throws Exception{
		return CommunityDao.getCommunityList();
	}
	
	//자유게시판 상세보기
	public CommunityDto getCommunity(int postNo)throws Exception {
		return CommunityDao.getCommunity(postNo);
	}
	
	//조회수 1 증가
	public void updateVw(int postNo) throws Exception{
		CommunityDao.updateVw(postNo);
	}
	
	//자유게시판 수정
	public int communityUpdate(CommunityDto community)throws Exception {
		return CommunityDao.communityUpdate(community);
	}

	//자유게시판 삭제
	public int communityDelete(int postNo)throws Exception {
		return CommunityDao.communityDelete(postNo);
	}


}
