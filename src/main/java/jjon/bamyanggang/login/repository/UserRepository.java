package jjon.bamyanggang.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jjon.bamyanggang.login.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
		
	Boolean existsByUsername(String username);
	
	UserEntity findByUsername(String username);
	void deleteByUsername(String username);
}
