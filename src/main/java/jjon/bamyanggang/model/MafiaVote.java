package jjon.bamyanggang.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("mafia_vote")
public class MafiaVote {
	
	private int roomNo;
	private int id;
	private String userId;
	private int voteCnt;
	
}
