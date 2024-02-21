package com.mfb.base.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	JwtUtils jwtUtils = new JwtUtils();
	//private final static Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			if (jwtUtils.checkJWTToken(request, response)) {
				Claims claims = jwtUtils.validateJwtToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			//logger.info("### filter url: " + request.getRequestURI());
			chain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json");
			response.getWriter().write(convertObjectToJson(e.getMessage()));
		}
	}

	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private String convertObjectToJson(String mensaje) throws JsonProcessingException {
		Map<String, Object> elements = new HashMap<String, Object>();
		elements.put("success", false);
		elements.put("message", mensaje);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(elements);
		return json;
	}
}
