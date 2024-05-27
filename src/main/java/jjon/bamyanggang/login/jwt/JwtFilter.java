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
			String authorization = request.getHeader("Authorization");
			
			if (authorization == null || !authorization.startsWith("Bearer " )) {
				filterChain.doFilter(request, response);
				return;				
			}
			
			String token = authorization.split(" ")[1];
			if (jwtUtil.isExpired(token)) {
				filterChain.doFilter(request, response);
				return;
			}
			UserEntity userEntity = new UserEntity();
			CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);		
			Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,  null, customUserDetails.getAuthorities());
		
			SecurityContextHolder.getContext().setAuthentication(authToken);
			System.out.println(authToken);
			filterChain.doFilter(request, response);
		}
	}
