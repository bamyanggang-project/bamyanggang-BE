package jjon.bamyanggang.reply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jjon.bamyanggang.model.ReplyDto;
import jjon.bamyanggang.reply.mapper.ReplyDao;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyDao replyDao;

	//댓글 목록
	public List<ReplyDto> getReplyList(int postNo)throws Exception {
		return replyDao.getReplyList(postNo);
	}

	//댓글작성
	public int replyInsert(ReplyDto reply)throws Exception {
		return replyDao.replyInsert(reply);
	}

	//댓글수정
	public int replyUpdate(ReplyDto reply)throws Exception {
		return replyDao.replyUpdate(reply);
	}

	//댓글삭제
	public int replyDelete(ReplyDto reply)throws Exception {
		return replyDao.replyDelete(reply);
	}

}
