package jjon.bamyanggang.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jjon.bamyanggang.model.NoticeDto;
import jjon.bamyanggang.notice.mapper.NoticeDao;

@Service
public class NoticeService{

	@Autowired
	private NoticeDao noticeDao;

	//공지사항 리스트
	public List<NoticeDto> getNoticeList() {
		return noticeDao.getNoticeList();
	}

	//조회수 증가
	public void updateVw(int postNo) {
		noticeDao.updateVw(postNo);
	}

	//공지사항 상세보기
	public NoticeDto getNotice(int postNo) {
		return noticeDao.getNotice(postNo);
	}

	//유효한 이전 글 postNo가져오기
	public int getPrevPostNo(int postNo) {
		return noticeDao.getPrevPostNo(postNo);
	}

	//유효한 다음 글 postNo가져오기
	public int getNextPostNo(int postNo) {
		return noticeDao.getNextPostNo(postNo);
	}
}
