package jjon.bamyanggang.login.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
	        	// 익명 클래스 생성
	            @Override
	            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	            	
	                CorsConfiguration configuration = new CorsConfiguration();
	                
	                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173")); // 허용된 오리진 설정
	                configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 HTTP 메서드 허용
	                configuration.setAllowCredentials(true); // 자격증명 허용 설정
	                configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
	                configuration.setMaxAge(3600L); // 사전 검사 결과 캐시 유지 시간 설정 (3600초 -> 1시간)
					configuration.setExposedHeaders(Collections.singletonList("Authorization")); // 노출할 헤더 설정 
					configuration.addExposedHeader("Authorization");

	                return configuration;
	                // 설정된 CORS 구성 반환
	            }
	        })));

	        //csrf disable
	    	// JWT 는 csrf공격에대한 보호가 필요하지않다.
	    	// 다만 세션기반의 인증방식을 사용하는경우 CSRF 공격에 보호가 필요하다.
	    	// JWT는 클라이언트(브라우저)에서 조작할수없게되있고 서버에서 JWT 유효성 확인을 진행하므로 클라이언트가 토큰 조작이 불가능하다.(JWT는 서명 또는 암호화가되어있음)
	        // 세션은 브라우저에 저장되는데 보안취약점을 가질수있는데 예를들어 쿠키에 저장될경우 다른공격자가 열람하거나 조작할수있음
	    	// 세션식별자를 가로채 세션에 접근하여 사용자로 위장할수있음. 보통의 세션식별자는 암호화가 되있지 않기때문이다. 
	    	http
	                .csrf((auth) -> auth.disable());

	        // 폼 로그인 방식 disable
	    	// http 기본 전송 방식 form이지만 restful api 에서는 보통 json 방식으로 보내기떄문에 disable
	        http
	                .formLogin((auth) -> auth.disable());

	        //http basic 인증 방식 disable
	        // http basic 방식은 사용자 이름과 비밀번호를 요청헤더에 직접 포함하여 보낸다. 그래서 보안에 취약하다.
	        http
	                .httpBasic((auth) -> auth.disable());

	        // url 관리하는 시큐리티 기능.
	        http
	                .authorizeHttpRequests((auth) -> auth
	                        .requestMatchers("/**").permitAll()
	                        .requestMatchers("/admin").hasRole("ADMIN")
	                        .anyRequest().authenticated());
	        				

	        http
	                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
	        //Jwt 필터 추가하는 부분,  Login필터 거친후에 jwtFilter 가 실행된다. JWT 검증 -> 로그인 완료
	        http
	                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);
	        // 로그인 필터가 사용자 인증 -> JWT 토큰 생성할때 , access + refresh 토큰 두개 다 발급
	        http
	        .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
	        // 로그아웃 요청 -> 서버에 도달하면 요청가로채기 -> 요청에서 JWT추출 -> JWT 로 사용자 정보확인 -> 발급된 리프레쉬 토큰 DB에서 삭제 -> 사용자의 세션종료 -> 사용자에게 로그아웃완료 응답
	        
	        
	        http
	                .sessionManagement((session) -> session
	                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	        // 세션 정책 설정 , 세션 생성하지않음 , Restful API와같이 상태를 유지하지않는 STATELESS 한 서비스에 적합.
	        /*
	         * REST API 가 왜 STATELESS(상태 유지안함)가 기본 특성인가 ?
	         * 클라이언트의 상태를 유지하지 않는다는것은 각각의 요청이 서로 독립적이며 이전 요청에 대한 정보를 기억하지 않는 다는것
	         * 일반적으로 상태를 유지하기위해 세션을 사용한다. 
	         * 
	         */
	        
	        
	        return http.build();
	    }
	}
