package jjon.bamyanggang.login.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import jjon.bamyanggang.login.jwt.CustomLogoutFilter;
import jjon.bamyanggang.login.jwt.JwtFilter;
import jjon.bamyanggang.login.jwt.JwtUtil;
import jjon.bamyanggang.login.jwt.LoginFilter;
import jjon.bamyanggang.login.repository.RefreshRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 private final AuthenticationConfiguration authenticationConfiguration;
	    private final JwtUtil jwtUtil;
	    private final RefreshRepository refreshRepository;	    
	    
	    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, RefreshRepository refreshRepository 
	    		) {

	        this.authenticationConfiguration = authenticationConfiguration;
	        this.jwtUtil = jwtUtil;
	        this.refreshRepository = refreshRepository;
	  
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }
	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }	    
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	http
	        .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
	            @Override
	            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	            	
	                CorsConfiguration configuration = new CorsConfiguration();
	                
	                configuration.setAllowedOrigins(Collections.singletonList("https://js1.jsflux.co.kr")); // 허용된 오리진 설정
	                configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 HTTP 메서드 허용
	                configuration.setAllowCredentials(true); // 자격증명 허용 설정
	                configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
	                configuration.setMaxAge(3600L); // 사전 검사 결과 캐시 유지 시간 설정 (3600초 -> 1시간)
			configuration.setExposedHeaders(Collections.singletonList("Authorization")); // 노출할 헤더 설정 
			configuration.addExposedHeader("Authorization");
			configuration.addExposedHeader("set-cookie");
				
			        
	                return configuration;
	            }
	        })));
	    	http
	                .csrf((auth) -> auth.disable());
	    
	        http
	                .formLogin((auth) -> auth.disable());

	        http
	                .httpBasic((auth) -> auth.disable());

	        http
	                .authorizeHttpRequests((auth) -> auth
	                        .requestMatchers("/**").permitAll()
	                        .requestMatchers("/admin").hasRole("ADMIN")
	                        .anyRequest().authenticated());
	        http
	                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
	        http
	                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);
	        http
	        	.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
	        http
	                .sessionManagement((session) -> session
	                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));	
	             	        
	        return http.build();
	    }
    

}


	    
	    
	
