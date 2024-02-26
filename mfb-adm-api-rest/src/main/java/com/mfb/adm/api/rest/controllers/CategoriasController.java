package com.mfb.adm.api.rest.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.mfb.adm.api.services.ICategoriaService;
import com.mfb.adm.comm.dtos.CategoriaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaCategorias;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/categorias")
public class CategoriasController {
	@Autowired
	ICategoriaService servicio;

	// servicios basicos
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.verPorId(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	public ResponseEntity<?> registrar(@RequestBody CategoriaDto dto) throws ParseException {
		RespuestaRest respuesta = servicio.registrar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody CategoriaDto dto) {
		RespuestaRest respuesta = servicio.actualizar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.eliminar(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	// otros servicios
	@GetMapping("/listar")
	public ResponseEntity<?> listarPorCriterios(@RequestParam(value = "idEmpresa", required = true) long idEmpresa,
			@RequestParam(value = "idsCategorias", required = false) String idsCategorias) {
		SolicitudBusquedaCategorias criterios = SolicitudBusquedaCategorias.builder().idEmpresa(idEmpresa)
				.idsCategorias(idsCategorias).build();
		RespuestaRest respuesta = servicio.listarPorCriterios(criterios);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
