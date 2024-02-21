package com.mfb.adm.api.rest.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mfb.adm.Auditor;
import com.mfb.adm.api.services.IPreguntaService;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaPreguntas;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;
import com.mfb.adm.comm.requests.SolicitudPregunta;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/preguntas")
public class PreguntasController {
	@Autowired
	IPreguntaService servicio;

	@PostMapping
	// @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> preguntar(@RequestBody SolicitudPregunta dto) throws ParseException {
		RespuestaRest respuesta = servicio.preguntar(dto, Auditor.getUsuario());
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/listar")
	public ResponseEntity<?> listarPorCriterios(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "fechaInicio", required = false) String fechaInicio,
			@RequestParam(value = "fechaFin", required = false) String fechaFin) throws ParseException {
		SolicitudBusquedaPreguntas criterios = SolicitudBusquedaPreguntas.builder().username(username)
				.fechaInicio(fechaInicio).fechaFin(fechaFin).build();
		RespuestaRest respuesta = servicio.listarPorCriterios(criterios);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/listar-paginado")
	public ResponseEntity<?> listarPaginado(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "cantidadItems", required = false, defaultValue = "0") Integer cantidadItems,
			@RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
			@RequestParam(value = "filtro", required = false, defaultValue = "") String filtro,
			@RequestParam(value = "campoOrden", required = false, defaultValue = "") String campoOrden,
			@RequestParam(value = "tipoOrden", required = false, defaultValue = "1") Integer tipoOrden) {
		SolicitudListaPaginada solicitud = SolicitudListaPaginada.builder().username(username).cantidadItems(cantidadItems).filtro(filtro)
				.campoOrden(campoOrden).tipoOrden(tipoOrden).pagina(pagina).build();
		RespuestaRest respuesta = servicio.listarPaginado(solicitud);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
