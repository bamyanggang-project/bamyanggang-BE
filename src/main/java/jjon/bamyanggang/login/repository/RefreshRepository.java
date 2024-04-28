package jjon.bamyanggang.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import jjon.bamyanggang.login.entity.RefreshEntity;


public interface RefreshRepository extends JpaRepository<RefreshEntity, Long >{

	
		Boolean existsByRefresh(String refresh);
		
		@Transactional
		void deleteByRefresh(String refresh);
}
