package jjon.bamyanggang.model;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("notice")
public class NoticeDto {

	private int postNo;
	private String title;
	private String content;
	private int vwCnt;
	private Date wrtnDate;
}
