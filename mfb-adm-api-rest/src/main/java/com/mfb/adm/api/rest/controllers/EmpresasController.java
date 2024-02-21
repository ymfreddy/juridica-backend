package com.mfb.adm.api.rest.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfb.adm.comm.dtos.EmpresaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.api.services.IEmpresaService;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/empresas")
public class EmpresasController {

	@Autowired
	IEmpresaService servicio;
	
	@Value("${redis.cacheable}")
	private String CON_CACHE;
	
	//@Autowired
    //private CacheManager cacheManager;

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> ver(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.verPorId(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/nit/{nit}")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> verPorNit(@PathVariable("nit") long nit) {
		RespuestaRest respuesta = servicio.verPorNit(nit);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> registrar(@RequestBody EmpresaDto dto) throws ParseException {
		RespuestaRest respuesta = servicio.registrar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> actualizar(@RequestBody EmpresaDto dto) {
		RespuestaRest respuesta = servicio.actualizar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> eliminar(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.eliminar(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> listar() {
		RespuestaRest respuesta = servicio.listar();
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
