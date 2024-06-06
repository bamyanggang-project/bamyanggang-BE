package jjon.bamyanggang.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class UserEntity {
// 로그인을 위한 Entity
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	
	@Column(name="user_id")
	private String  username;
	@Column(name="user_pw")
	private String  password;
	@Column(name="user_authority")
	private String  role;
	
}
