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

//SecurityConfig 클래스는 Spring Security의 설정을 정의하는 클래스입니다.
//이 클래스는 인증 구성(AuthenticationConfiguration), JWT 유틸리티(JwtUtil), 그리고 RefreshRepository를 주입받아 사용합니다.
public class SecurityConfig {
// 시큐리티 인증설정 클래스
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
	    

	    // SecurityFilterChain을 구성하는 메서드입니다. CORS 설정을 정의합니다.

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	      http
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Collections.singletonList(allowedOrigin)); // 환경 변수로 주입된 오리진 설정
                configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 HTTP 메서드 허용
                configuration.setAllowCredentials(true); // 자격증명 허용 설정
                configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
                configuration.setMaxAge(3600L); // 사전 검사 결과 캐시 유지 시간 설정 (3600초 -> 1시간)
                configuration.addExposedHeader("Authorization"); // 노출할 헤더 설정
                configuration.addExposedHeader("Set-Cookie");
                return configuration;
            }))
            .csrf(csrf -> csrf.disable()) // 세션을 쓰지않아서 disable
            .formLogin(form -> form.disable()) // json 로그인 -> form 로그인 disable
            .httpBasic(httpBasic -> httpBasic.disable()) // http 기본 인증 disable -> JWT 인증 사용할거기 때문에
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class) 
            .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	
        return http.build();

	    }
    

}


	    
	    
	
