package jjon.bamyanggang.reply.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jjon.bamyanggang.model.ReplyDto;

@Mapper
public interface ReplyDao {

	//댓글 목록
	public List<ReplyDto> getReplyList(int postNo)throws Exception;

	//댓글 작성
	public int replyInsert(ReplyDto reply)throws Exception;

	//댓글수정
	public int replyUpdate(ReplyDto reply)throws Exception;

	//댓글삭제
	public int replyDelete(ReplyDto reply)throws Exception;
	
	
}
