package com.mfb.adm.api.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfb.adm.api.services.IRecursoService;
import com.mfb.adm.comm.dtos.RespuestaRest;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/recursos")
public class RecursosController {
	@Autowired
	IRecursoService servicio;

	@GetMapping
	public ResponseEntity<?> listar() {
		RespuestaRest respuesta = servicio.listar();
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
