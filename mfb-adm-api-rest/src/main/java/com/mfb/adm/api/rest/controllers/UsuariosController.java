package com.mfb.adm.api.rest.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.mfb.adm.comm.dtos.ActualizarPasswordDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.dtos.UsuarioRegistroClienteDto;
import com.mfb.adm.comm.dtos.UsuarioRegistroDto;
import com.mfb.adm.comm.requests.SolicitudBusquedaUsuarios;
import com.mfb.adm.Auditor;
import com.mfb.adm.api.services.IUsuarioService;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/usuarios")
public class UsuariosController {
	@Autowired
	IUsuarioService servicio;

	BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();

	// servicios basicos
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> ver(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.verPorId(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> registrar(@RequestBody UsuarioRegistroDto dto) throws ParseException {
		dto.setPassword(crypt.encode(dto.getPassword().trim()));
		RespuestaRest respuesta = servicio.registrar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/usuarios-clientes")
	public ResponseEntity<?> registrarCliente(@RequestBody UsuarioRegistroClienteDto dto) throws ParseException {
		dto.setPassword(crypt.encode(dto.getPassword().trim()));
		RespuestaRest respuesta = servicio.registrarCliente(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/actualizar-password")
	public ResponseEntity<?> cambioPassword(@RequestBody ActualizarPasswordDto dto) throws ParseException {
		dto.setPassword(crypt.encode(dto.getPassword().trim()));
		RespuestaRest respuesta = servicio.actualizarPassword(dto.getUsername(), dto.getPassword(), Auditor.getUsuario());
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> actualizar(@RequestBody UsuarioRegistroDto dto) {
		RespuestaRest respuesta = servicio.actualizar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	public ResponseEntity<?> eliminar(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.eliminar(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	// otros servicios
	@GetMapping("/listar")
	public ResponseEntity<?> listarPorCriterios(
			@RequestParam(value = "idEmpresa", required = true) long idEmpresa,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "idTipoUsuario", required = false) Integer idTipoUsuario,
			@RequestParam(value = "resumen", required = false, defaultValue = "false") boolean resumen) {
		SolicitudBusquedaUsuarios criterios = SolicitudBusquedaUsuarios.builder().idEmpresa(idEmpresa)
				.username(username).idTipoUsuario(idTipoUsuario).resumen(resumen).build();
		RespuestaRest respuesta = servicio.listarPorCriterios(criterios);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/session")
	public ResponseEntity<?> session() {
		RespuestaRest respuesta = servicio.obtenerSession(Auditor.getUsuario());
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/listar-opciones")
	public ResponseEntity<?> listarOpciones() {
		RespuestaRest respuesta = servicio.listarOpciones();
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
