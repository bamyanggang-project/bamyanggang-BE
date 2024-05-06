package jjon.bamyanggang.model;



import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("reply")
public class ReplyDto {
	
	private int replyNo;    //댓글 번호
	private int postNo;     //글 번호
	private String userId;  //사용자 아이디
	private String content;  //내용
	private Date wrtnDate;  //등록일자
	
}
