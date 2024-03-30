package com.diginamic.apijava.securityConfig;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	
	//Cors configuration
	@Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		String encodingId = "bcrypt";
		return new DelegatingPasswordEncoder(encodingId, Map.of(encodingId, new BCryptPasswordEncoder()));
	}
	
	@Scope("prototype")
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc, JWTAuthorizationFilter jwtFilter, JWTConfig jwtConfig, @Qualifier("corsConfiguration") CorsConfigurationSource source) throws Exception {
		
		http.authorizeHttpRequests(
				auth -> auth				 
				//.requestMatchers(mvc.pattern(HttpMethod.GET, "rest/person")).permitAll()
				.requestMatchers(mvc.pattern(HttpMethod.POST, "api/signup")).permitAll()
				.requestMatchers(mvc.pattern(HttpMethod.POST, "api/signin")).permitAll()
				//.requestMatchers(mvc.pattern(HttpMethod.GET, "admin")).hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			// Spring Security va valoriser un jeton stocké dans un cookie XSRF-TOKEN
        	// Le client souhaitant effectuer une requête de modification (POST par exemple)
        	// devra valoriser une entête HTTP "X-XSRF-TOKEN" avec le jeton.
			.csrf(
				csrf -> csrf.disable()
//				csrf -> csrf
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler()::handle)
//				//h2-console
//				.ignoringRequestMatchers(PathRequest.toH2Console())
			)
//			.headers(
//		        //h2-console
//				headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
//		     )
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			//Cors configuration
			.cors(cors -> cors.configurationSource(source))
			.logout(logout -> logout
					.logoutUrl("/api/signout")
					.logoutSuccessHandler((req, resp, auth) -> resp.setStatus(HttpStatus.OK.value()))
					.deleteCookies(jwtConfig.getCookie()));
		
		return http.build();
		
	}

}
