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

import com.mfb.adm.api.services.IClienteService;
import com.mfb.adm.comm.dtos.ClienteDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaClientes;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/clientes")
public class ClientesController {
	@Autowired
	IClienteService servicio;

	// servicios basicos
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.verPorId(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/telefonos/{idEmpresa}/{codigoCliente}")
	public ResponseEntity<?> ver(@PathVariable("idEmpresa") long idEmpresa, @PathVariable("codigoCliente") String codigoCliente) {
		RespuestaRest respuesta = servicio.verTelefonoPorIdEmpresaYCodigo(idEmpresa, codigoCliente);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	public ResponseEntity<?> registrar(@RequestBody ClienteDto dto) throws ParseException {
		RespuestaRest respuesta = servicio.registrar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody ClienteDto dto) {
		RespuestaRest respuesta = servicio.actualizar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.eliminar(id);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/listar")
	public ResponseEntity<?> listarPorCriterios(
			@RequestParam(value = "idEmpresa", required = true) long idEmpresa,
			@RequestParam(value = "codigoCliente", required = false) String codigoCliente,
			@RequestParam(value = "codigoTipoDocumento", required = false) Integer codigoTipoDocumento,
			@RequestParam(value = "resumen", required = false, defaultValue = "false") boolean resumen,
			@RequestParam(value = "cantidadRegistros", required = false, defaultValue = "0") Integer cantidadRegistros,
		    @RequestParam(value = "termino", required = false) String termino) {
		SolicitudBusquedaClientes criterios = SolicitudBusquedaClientes.builder().idEmpresa(idEmpresa).cantidadRegistros(cantidadRegistros).termino(termino)
				.codigoCliente(codigoCliente).codigoTipoDocumento(codigoTipoDocumento).resumen(resumen).build();
		RespuestaRest respuesta = servicio.listarPorCriterios(criterios);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/listar-paginado")
	public ResponseEntity<?> listarPaginado(
			@RequestParam(value = "idEmpresa", required = true) long idEmpresa,
			@RequestParam(value = "cantidadItems", required = false, defaultValue = "0") Integer cantidadItems,
			@RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
			@RequestParam(value = "filtro", required = false, defaultValue = "") String filtro,
			@RequestParam(value = "campoOrden", required = false, defaultValue = "") String campoOrden,
			@RequestParam(value = "tipoOrden", required = false, defaultValue = "1") Integer tipoOrden) {
		SolicitudListaPaginada solicitud = SolicitudListaPaginada.builder().idEmpresa(idEmpresa).cantidadItems(cantidadItems).filtro(filtro)
				.campoOrden(campoOrden).tipoOrden(tipoOrden).pagina(pagina).build();
		RespuestaRest respuesta = servicio.listarPaginado(solicitud);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

}
