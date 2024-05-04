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
	
	public class JwtFilter extends OncePerRequestFilter {
		
		private final JwtUtil jwtUtil;
		
		public JwtFilter(JwtUtil jwtUtil) {
			this.jwtUtil = jwtUtil;
		}
	
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			//request에서 atuhorization 헤더를 찾음
			String authorization = request.getHeader("Authorization");
			
			// Authorizaon 헤더 검증
			if (authorization == null || !authorization.startsWith("Bearer " )) {
				
				System.out.println("token null");
				filterChain.doFilter(request, response);
				
				// 조건이 해당되면 메소드 종료(필수)
				return;
				
			}
			
			System.out.println("authorizaion now");
			// Bearer 부분 제거 후 순수 토큰만 흭득
			String token = authorization.split(" ")[1];
			System.out.println(token);
			// 토큰 소멸시간 검증 
			if (jwtUtil.isExpired(token)) {
				
				System.out.println("token expired");
				filterChain.doFilter(request, response);
				
				// 조건이 해당되면 메소드 종료(필수)
				return;
			}
			
			
			// 토큰에서 username 과 role 흭득
			
			

			UserEntity userEntity = new UserEntity();
		
			
			CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
			
			Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,  null, customUserDetails.getAuthorities());
		
			SecurityContextHolder.getContext().setAuthentication(authToken);
			System.out.println(authToken);
			filterChain.doFilter(request, response);
		}
		
	
	}
