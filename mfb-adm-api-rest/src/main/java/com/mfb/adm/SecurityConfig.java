package com.mfb.adm;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
		.antMatchers(HttpMethod.GET, "/adm/api/v1/imagenes/descargar/*")
		.antMatchers(HttpMethod.GET, "/adm/api/v1/login/refresh-token")
		.antMatchers(HttpMethod.POST, "/adm/api/v1/usuarios/usuarios-clientes");
	}

}