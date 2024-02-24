package com.mfb.adm.api.rest.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mfb.adm.api.services.ICitaService;
import com.mfb.adm.comm.dtos.CitaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaCitas;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/citas")
public class CitasController {
	@Autowired
	ICitaService servicio;

	BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();

	// servicios basicos
	@GetMapping("/estados")
	public ResponseEntity<?> listarEstados() {
		RespuestaRest respuesta = servicio.listarEstados();
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	// servicios basicos
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable("id") String id) {
		RespuestaRest respuesta = servicio.verPorId(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	public ResponseEntity<?> registrar(@RequestBody CitaDto dto) throws ParseException {
		RespuestaRest respuesta = servicio.registrar(dto);
		if (respuesta.isSuccess()) {
			CitaDto cita = (CitaDto) servicio.verPorId(respuesta.getContent().toString()).getContent();
			respuesta.setContent(cita);
		}
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody CitaDto dto) throws ParseException {
		RespuestaRest respuesta = servicio.actualizar(dto);
		if (respuesta.isSuccess()) {
			CitaDto cita = (CitaDto) servicio.verPorId(respuesta.getContent().toString()).getContent();
			respuesta.setContent(cita);
		}
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/actualizar-fecha")
	public ResponseEntity<?> actualizarFecha(@RequestBody CitaDto dto) throws ParseException {
		RespuestaRest respuesta = servicio.actualizarFecha(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") String id) {
		RespuestaRest respuesta = servicio.eliminar(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	// otros servicios
	@GetMapping("/listar")
	public ResponseEntity<?> listarPorCriterios(@RequestParam(value = "idEmpresa", required = true) long idEmpresa,
			@RequestParam(value = "codigoCliente", required = false) String codigoCliente,
			@RequestParam(value = "tiposCita", required = false) String tiposCita,
			@RequestParam(value = "estadosCita", required = false) String estadosCita,
			@RequestParam(value = "resumen", required = false, defaultValue = "false") boolean resumen) {
		SolicitudBusquedaCitas criterios = SolicitudBusquedaCitas.builder().idEmpresa(idEmpresa)
				.codigoCliente(codigoCliente).tiposCita(tiposCita).resumen(resumen).estadosCita(estadosCita).build();
		RespuestaRest respuesta = servicio.listarPorCriterios(criterios);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
