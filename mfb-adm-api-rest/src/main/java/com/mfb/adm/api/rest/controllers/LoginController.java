package com.mfb.adm.api.rest.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfb.adm.api.rest.utils.DatosRequest;
import com.mfb.adm.api.services.ILoginService;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudLogin;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/login")
public class LoginController {

	@Autowired
	private ILoginService servicio;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@PostMapping
	public ResponseEntity<?> autenticarUsuario(@RequestBody SolicitudLogin request) throws SQLException, Exception {
		logger.info(request.toString());
		RespuestaRest respuesta = servicio.login(request);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
		RespuestaRest respuesta = servicio.refrescarToken(DatosRequest.getToken());
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}



}
