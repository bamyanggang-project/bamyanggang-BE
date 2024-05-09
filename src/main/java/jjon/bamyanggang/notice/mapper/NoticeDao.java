package jjon.bamyanggang.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jjon.bamyanggang.model.NoticeDto;

@Mapper
public interface NoticeDao {

	//공지사항 목록
	public List<NoticeDto> getNoticeList();

	//조회수 증가
	public void updateVw(int postNo);

	//공지 상세보기
	public NoticeDto getNotice(int postNo);

	//이전 글번호 불러오기
	public int getPrevPostNo(int postNo);

	//다음 글번호 불러오기
	public int getNextPostNo(int postNo);

}
