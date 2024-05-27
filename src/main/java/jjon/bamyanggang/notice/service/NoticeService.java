package jjon.bamyanggang.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jjon.bamyanggang.model.NoticeDto;
import jjon.bamyanggang.notice.mapper.NoticeDao;

@Service
public class NoticeService{

	@Autowired
	private NoticeDao noticeDao;

	public List<NoticeDto> getNoticeList(Map m) {
		return noticeDao.getNoticeList(m);
	}

	public void updateVw(int postNo) {
		noticeDao.updateVw(postNo);
	}

	public NoticeDto getNotice(int postNo) {
		return noticeDao.getNotice(postNo);
	}

	public int getPrevPostNo(int postNo) {
		return noticeDao.getPrevPostNo(postNo);
	}

	public int getNextPostNo(int postNo) {
		return noticeDao.getNextPostNo(postNo);
	}
}
