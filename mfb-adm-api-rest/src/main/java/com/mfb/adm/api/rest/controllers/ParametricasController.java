package com.mfb.adm.api.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.api.services.IParametricaService;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/parametricas")
public class ParametricasController {
	@Autowired
	IParametricaService servicio;

	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.verPorId(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/listarPorTipo/{tipo}")
	public ResponseEntity<?> verPorTipo(@PathVariable("tipo") String tipo) {
		RespuestaRest respuesta = servicio.listarPorTipo(tipo);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
