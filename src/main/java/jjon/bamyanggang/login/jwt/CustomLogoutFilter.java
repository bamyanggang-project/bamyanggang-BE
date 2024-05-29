package jjon.bamyanggang.login.jwt;

import java.io.IOException;
import org.springframework.web.filter.GenericFilterBean;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jjon.bamyanggang.login.repository.RefreshRepository;

public class CustomLogoutFilter extends GenericFilterBean {
	
	private final JwtUtil jwtUtil;
	private final RefreshRepository refreshRepository;
	
	public CustomLogoutFilter (JwtUtil jwtUtil, RefreshRepository refreshRepository) {
		
		this.jwtUtil = jwtUtil;
		this.refreshRepository = refreshRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}
	
	private void doFilter(HttpServletRequest requset, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
	
	//path and method verify
		
	String requsetUri = requset.getRequestURI();
	if(!requsetUri.matches("^\\/api\\/logout$")) {
		
		filterChain.doFilter(requset, response);	
		return;
	}
	// 로그아웃 uri 확인인
	String requestMetgod = requset.getMethod();
	if(!requestMetgod.equals("POST")) {
		
		filterChain.doFilter(requset, response);
		return;
	}
	// 메서드 post 인지 확인 
		
	//get refresh token
	String refresh = null;
	Cookie[] cookies = requset.getCookies();
	for (Cookie cookie : cookies) {
		
		if(cookie.getName().equals("refresh")) {
			
			refresh = cookie.getValue();
		}
	}
	
	// refresh null check
	if (refresh == null)  {
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	// expired check
	
	try  {
		jwtUtil.isExpired(refresh);
		
	} catch (ExpiredJwtException e) {
		
		//response status code
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	String category = jwtUtil.getCategory(refresh);
	if(!category.equals("refresh") ) {
		
		//response status code
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	Boolean isExist = refreshRepository.existsByRefresh(refresh);
	
	if(!isExist) {
		
		//response status code
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	refreshRepository.deleteByRefresh(refresh);
	Cookie cookie = new Cookie("refresh", null);
	cookie.setMaxAge(0);
	// 로그아웃시 어디로 보낼것인지
	cookie.setPath("/");
	
	response.addCookie(cookie);
	response.setStatus(HttpServletResponse.SC_OK);
	}
} 
