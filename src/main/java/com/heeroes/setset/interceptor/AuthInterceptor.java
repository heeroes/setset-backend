package com.heeroes.setset.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.heeroes.setset.user.utils.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor{
	private final JwtTokenProvider jwtTokenProvider; 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//단순 조회 요청과 preflight 요청인 경우, true 로 넘김
		String method = request.getMethod();
		log.debug("AuthInterceptor()의 preHandle실행 method:{}", method);
		
		// 공유 시, 상세 여행 계획 페이지 접근 가능하도록
		if(request.getRequestURI().contains("plan/") && method.equals("GET"))
			return true;
		
//		if(method.equals("GET") || method.equals("OPTIONS")) return true;
		
		String tokenHeader = request.getHeader("Authorization");	//Header에서 토큰 정보 추출
		//토큰 헤더가 없거나 Bearer로 시작하지 않는 경우
		if(tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
			response.setStatus(401);
			response.getWriter().write("Unauthorized");
			return false;
		}
		//토큰이 유효하지 않은 경우
		String token = tokenHeader.substring(7);
		if(!jwtTokenProvider.isValid(token) ) {	
			response.setStatus(401);
			response.getWriter().write("Unauthorized");
			return false;
		}
		
		//토큰이 유효한 경우
		return true;
	}
}
