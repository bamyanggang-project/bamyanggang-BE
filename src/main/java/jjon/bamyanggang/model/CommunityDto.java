package jjon.bamyanggang.model;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("community")
public class CommunityDto {
	
	private int postNo; 	 //글번호
	private String userId;   //작성자
	private String title;    //글제목
	private String content;  //글내용 
	private String img;      //이미지
	private int vwCnt;       //조회수
	private Date wrtnDate;   //등록날짜
	private int prevPostNo;  // 이전글 번호
	private int nextPostNo;  //다음글 번호
}
