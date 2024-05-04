package jjon.bamyanggang.model;

import java.util.List;

import lombok.Data;

@Data
public class ChatDTO {
	
	private String roomId;
	private String userNicknm;
	private List<String> userNicknmList;
	
}
