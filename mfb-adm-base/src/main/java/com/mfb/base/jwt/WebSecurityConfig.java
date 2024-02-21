package com.mfb.base.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtUtils jwtUtils;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable()
				.addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/").permitAll().antMatchers("/api/v1/login").permitAll()
				.antMatchers("/adm/api/v1/login").permitAll()
				.antMatchers("/sfe/api/v1/login").permitAll()
				.antMatchers("/v2/api-docs").permitAll().antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/swagger-ui.html").permitAll().antMatchers("/configuration/**").permitAll()
				.antMatchers("/api/v1/imagenes/descargar/**").permitAll()
				.antMatchers("/webjars/**").permitAll().antMatchers("/public").permitAll().antMatchers("/**/public/**")
				.permitAll().antMatchers("/**/**/public/**").permitAll()
				// all other requests need to be authenticated
				.anyRequest().authenticated();

		httpSecurity.cors();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		List<String> lista = new ArrayList<String>();
		lista.add(HttpMethod.GET.name());
		lista.add(HttpMethod.PUT.name());
		lista.add(HttpMethod.POST.name());
		lista.add(HttpMethod.DELETE.name());
		configuration.setAllowedMethods(lista);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
		return source;
	}

}
