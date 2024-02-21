package com.mfb.adm.api.rest.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mfb.adm.comm.dtos.RespuestaRest;

@CrossOrigin
@RestController
public class TestConexionController {

	Date fechaServicio;

	public TestConexionController() {
		super();
		fechaServicio = new Date();
	}

	@GetMapping("/")
	public ResponseEntity<?> testConexion() throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = request.getRemoteAddr();
		Date actual = new Date();
		long memoriaTotal = Runtime.getRuntime().totalMemory() / 1000000;
		// limpiar la memoria
		long memoriaLibre = Runtime.getRuntime().freeMemory() / 1000000;
		// current heap usage
		long heapUsado = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
		System.gc();
		OutputStream.nullOutputStream().flush();
		String respuesta = "fecha servicio mfb-adm: " + fechaServicio + ", fecha y hora actual: " + actual
				+ ", ip:" + ip + ", memoriaTotal:" + memoriaTotal + ", memoriaLibre:" + memoriaLibre + ", heapUsado:"
				+ heapUsado;
		return new ResponseEntity<>(RespuestaRest.builder().success(true).message(respuesta).build(), HttpStatus.OK);
	}

}
