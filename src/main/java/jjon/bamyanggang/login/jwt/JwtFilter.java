	package jjon.bamyanggang.login.jwt;
	
	import java.io.IOException;
	
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.web.filter.OncePerRequestFilter;
	
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import jjon.bamyanggang.login.dto.CustomUserDetails;
	import jjon.bamyanggang.login.entity.UserEntity;

	
	// JWT 토큰을 처리하여 JWT 검증을 수행하는 필터 클래스
	public class JwtFilter extends OncePerRequestFilter {
		// JWT 발급.
		// 시큐리티 OncePerRequestFilter extends 해서 doFilterInternal 커스텀
		
		private final JwtUtil jwtUtil;		
		public JwtFilter(JwtUtil jwtUtil) {
			this.jwtUtil = jwtUtil;
		}
		
		
		/* 
		 * OncePerRequestFilter 상속받아 dofilterInternal 메서드를 오버라이딩하여
		   JWT로 커스텀 진행
		 */

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			String authorization = request.getHeader("Authorization");
			
			if (authorization == null || !authorization.startsWith("Bearer " )) {
				// 토큰이 존재하지 않거나 Bearer 토큰이 아닌 경우
				System.out.println("token null");
				filterChain.doFilter(request, response); // 다음 필터로 요청 전달
				
				// 조건이 해당되면 메소드 종료(필수)
				return;
				
			}
			
			String token = authorization.split(" ")[1];
			// 토큰 소멸시간 검증 
			if (jwtUtil.isExpired(token)) {
				// 토큰이 만료된 경우
				filterChain.doFilter(request, response); // 다음 필터로 요청 전달
				
				return;
			}

			
			
	
			
			// 토큰에서 사용자 정보 추출
			UserEntity userEntity = new UserEntity();
			// 사용자 정보를 기반으로 UserDetails 객체 생성
			CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
			 // Authentication 객체 생성
			Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,  null, customUserDetails.getAuthorities());
			// SecurityContextHolder에 Authentication 객체 설정
			SecurityContextHolder.getContext().setAuthentication(authToken);
			filterChain.doFilter(request, response); // 다음 필터로 요청 전달
		}
	}
