package com.mfb.adm.api.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.RespuestaLogin;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.dtos.UsuarioLogin;
import com.mfb.adm.comm.enums.EnumTipoUsuario;
import com.mfb.adm.comm.requests.SolicitudLogin;
import com.mfb.adm.api.services.ILoginService;
import com.mfb.adm.core.query.ILoginQuery;
import com.mfb.base.jwt.JwtUtils;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	ILoginQuery repositorio;

	@Autowired
	JwtUtils token;

	@Value("${jwt.duracion}")
	private Integer minutosDuracion;

	@Override
	public RespuestaRest login(SolicitudLogin request) throws IOException {
		BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
		UsuarioLogin existe = repositorio.login(request.getUsername().trim().replace(" ", ""));
		if (existe == null) {
			// return
			// RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_USER_NOT_EXIST).build();
			return RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_ERROR).build();
		}
		if (!crypt.matches(request.getPassword().trim(), existe.getPassword())) {
			// deshabilitar usuario despues de 5 intentos
			// return
			// RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_USER_NOT_EXIST).build();
			return RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_ERROR).build();
		}

		if (!existe.getEnabled()) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_USER_DISABLED).build();
		}

		// get roles
		List<String> roles = EnumTipoUsuario.getRoles(existe.getIdTipoUsuario());
		RespuestaLogin res = new RespuestaLogin();
		// generar token
		res.setToken(token.getJWTToken(existe.getUsername(), existe.getNit(), roles, minutosDuracion));
		res.setNit(existe.getNit());
		res.setIdEmpresa(existe.getIdEmpresa());
		res.setUsuario(existe.getUsername());
		return RespuestaRest.builder().success(true).content(res).message(MsgApp.RESPONSE_LOGIN_SUCCESSFULLY).build();
	}

	@Override
	public RespuestaRest refrescarToken(String request) throws Exception {
		if (request==null || request.isEmpty()) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_JWT_ERROR).build();
		}
		String claim = token.getUsernameFromToken(request);
		UsuarioLogin existe = repositorio.login(claim.split(":")[0]);
		if (existe == null) {
			// return
			// RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_USER_NOT_EXIST).build();
			return RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_ERROR).build();
		}

		if (!existe.getEnabled()) {
			return RespuestaRest.builder().message(MsgApp.RESPONSE_LOGIN_USER_DISABLED).build();
		}

		// get roles
		List<String> roles = EnumTipoUsuario.getRoles(existe.getIdTipoUsuario());
		// generar token
		String nuevoToken = token.getJWTToken(existe.getUsername(), existe.getNit(), roles, minutosDuracion);
		return RespuestaRest.builder().success(true).content(nuevoToken).message(MsgApp.RESPONSE_JWT_REFRESH).build();
	}

}
