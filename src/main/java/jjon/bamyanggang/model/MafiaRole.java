package jjon.bamyanggang.model;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("mafia_role")
public class MafiaRole {
	
	private String role;
	private int roomNo;
	private String userId;
	private int roleSt;
	private int master;
	private Timestamp entryTime;
	
}
