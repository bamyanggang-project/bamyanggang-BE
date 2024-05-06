package jjon.bamyanggang.login.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Password;
import jjon.bamyanggang.login.entity.UserEntity;
import jjon.bamyanggang.login.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public void deleteByUsername(String username ) {
		
		UserEntity user = userRepository.findByUsername(username);
		
		if(user != null ) {
			
			userRepository.delete(user);
			
		} else {
			throw new RuntimeException("비밀번호가 틀렸습니다");
		}
		
	}
	
	
	
	
}
