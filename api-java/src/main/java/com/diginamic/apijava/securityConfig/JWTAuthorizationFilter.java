package com.diginamic.apijava.securityConfig;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.diginamic.apijava.exception.JsonWebTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private JWTConfig jwtConfig;

	public JWTAuthorizationFilter(JWTConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		if(req.getCookies() != null) {
			Stream.of(req.getCookies())
				.filter(cookie -> cookie.getName().equals(jwtConfig.getCookie()))
				.map(Cookie::getValue)
				.forEach(token -> {
					try {
						Claims body = Jwts
								.parserBuilder()
								.setSigningKey(jwtConfig.getSecretKey())
								.build()
								.parseClaimsJws(token)
								.getBody();
						
						String username = body.getSubject();
						
						List<String> roles = body.get("roles", List.class);
						
						List<SimpleGrantedAuthority> authorities = roles
								.stream()
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList());
						
						Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
						
						SecurityContextHolder.getContext().setAuthentication(authentication);
					} catch (Exception e) {
						throw new JsonWebTokenException(e.getMessage());
					}
				});
		}
		
		chain.doFilter(req, res);
	}
}
