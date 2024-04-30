package jjon.bamyanggang.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jjon.bamyanggang.model.NoticeDto;
import jjon.bamyanggang.notice.mapper.NoticeDao;

@Service
public class NoticeServiceImpl{

	@Autowired
	private NoticeDao noticeDao;

	public List<NoticeDto> getNoticeList() {
		return noticeDao.getNoticeList();
	}

	public void updateVw(int postNo) {
		noticeDao.updateVw(postNo);
	}

	public NoticeDto getNotice(int postNo) {
		return noticeDao.getNotice(postNo);
	}
}
