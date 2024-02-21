package com.mfb.base.jwt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	public final String RUTA_ARCHIVO = "/opt/config/secretKey.txt";
	public final String HEADER = "Authorization";
	public final String PREFIX = "Bearer ";
	private final int DURACION_MINUTOS_POR_DEFECTO = 10;

	private String secretKey;

	public String getJWTToken(String usuario, Long nitEmpresa, List<String> roles, Integer minutos) throws IOException {
		int tiempo = (minutos == null || minutos <= 0) ? DURACION_MINUTOS_POR_DEFECTO : minutos;
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(String.join(",", roles));
		UUID uuid = UUID.randomUUID();

		String token = Jwts.builder().setId(uuid.toString()).setSubject(usuario + ":" + nitEmpresa)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (60000 * tiempo)))
				.signWith(SignatureAlgorithm.HS512, this.getSecretKey().getBytes()).compact();

		return token;
	}

	public String getUsernameFromToken(String token) throws Exception {
		Claims claims = Jwts.parser().setSigningKey(this.getSecretKey().getBytes()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public List<String> getRolesFromToken(String token) throws Exception {
		Claims claims = Jwts.parser().setSigningKey(this.getSecretKey().getBytes()).parseClaimsJws(token).getBody();
		List<String> roles = claims.get("authorities", List.class);
		return roles;

	}

	public boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		return authenticationHeader != null;
	}

	public Claims validateJwtToken(HttpServletRequest request)
			throws JwtTokenMalformedException, JwtTokenMissingException {
		try {
			String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
			return Jwts.parser().setSigningKey(this.getSecretKey().getBytes()).parseClaimsJws(jwtToken).getBody();

		} catch (IOException e) {
			throw new JwtTokenMalformedException("LLAVE INEXISTENTE");
		} catch (SignatureException e) {
			throw new JwtTokenMalformedException("FIRMA INVALIDA");
		} catch (MalformedJwtException e) {
			throw new JwtTokenMalformedException("TOKEN INVALIDO");
		} catch (ExpiredJwtException e) {
			throw new JwtTokenMalformedException("LA SESION HA EXPIRADO");
		} catch (UnsupportedJwtException e) {
			throw new JwtTokenMalformedException("TOKEN NO SOPORTADO");
		} catch (IllegalArgumentException e) {
			throw new JwtTokenMissingException("JWT CLAIMS STRING IS EMPTY");
		}
	}

	public String getSecretKey() throws IOException {
		if (secretKey == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				secretKey = "f613f._T3.zsd&/%G.#g32.&/.39";
			} else {
				File file = new File(RUTA_ARCHIVO);
				BufferedReader br;
				br = new BufferedReader(new FileReader(file));
				this.secretKey = br.readLine();
				br.close();
			}
		}
		return this.secretKey;
	}

}
