package com.mfb.adm.api.rest.controllers;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

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

import com.mfb.adm.api.services.IProductoService;
import com.mfb.adm.comm.dtos.ProductoDto;
import com.mfb.adm.comm.dtos.ProductoResumenDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaProductos;
import com.mfb.adm.comm.requests.SolicitudCargaMasivaProductos;
import com.mfb.adm.comm.requests.SolicitudListaPaginadaProductos;

@CrossOrigin
@RestController
@RequestMapping("${api.endpoint}/api/v1/productos")
public class ProductosController {
	@Autowired
	IProductoService servicio;

	// servicios basicos
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable("id") long id) {
		RespuestaRest respuesta = servicio.verPorId(id);
		// obtener datos adicionales
		if (respuesta.isSuccess() && respuesta.getContent()!=null) {
			@SuppressWarnings("unchecked")
			ProductoDto object = (ProductoDto) respuesta.getContent();
			servicio.cargarDatosAdicionales(object, 0L);
			respuesta.setContent(object);
		}
		
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	public ResponseEntity<?> registrar(@Valid @RequestBody ProductoDto dto) throws ParseException {
		RespuestaRest respuesta = servicio.registrar(dto);
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/masivos")
	public ResponseEntity<?> registrarMasivo(@Valid @RequestBody SolicitudCargaMasivaProductos solicitud) throws ParseException {
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody ProductoDto dto) {
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
			@RequestParam(value = "idEmpresa", required = true, defaultValue = "0") long idEmpresa,
			@RequestParam(value = "idSucursal", required = false, defaultValue = "0") long idSucursal,
			@RequestParam(value = "idProducto", required = false, defaultValue = "0") Long idProducto,
			@RequestParam(value = "codigoProducto", required = false) String codigoProducto,
			@RequestParam(value = "codigosTipoProducto", required = false) String codigosTipoProducto,
			@RequestParam(value = "idCategoria", required = false, defaultValue = "0") long idCategoria,
			@RequestParam(value = "idsCategorias", required = false) String idsCategorias,
			@RequestParam(value = "resumen", required = false, defaultValue = "false") boolean resumen,
			@RequestParam(value = "cantidadRegistros", required = false, defaultValue = "0") Integer cantidadRegistros,
		    @RequestParam(value = "termino", required = false) String termino) {
		SolicitudBusquedaProductos criterios = SolicitudBusquedaProductos.builder().idEmpresa(idEmpresa).codigoProducto(codigoProducto).resumen(resumen).cantidadRegistro(cantidadRegistros)
				.idsCategorias(idsCategorias).idCategoria(idCategoria).idSucursal(idSucursal).codigosTipoProducto(codigosTipoProducto).termino(termino)
				.idProducto(idProducto).build();
		RespuestaRest respuesta = servicio.listarPorCriterios(criterios);
		// obtener datos adicionales
		if (respuesta.isSuccess() && respuesta.getContent()!=null) {
			if (criterios.getResumen()) {
				@SuppressWarnings("unchecked")
				List<ProductoResumenDto> lista = (List<ProductoResumenDto>) respuesta.getContent();
				servicio.cargarResumenDatosAdicionales(lista, idSucursal);
				respuesta.setContent(lista);
			}
			else {
				@SuppressWarnings("unchecked")
				List<ProductoDto> lista = (List<ProductoDto>) respuesta.getContent();
				servicio.cargarDatosAdicionales(lista, idSucursal);
				respuesta.setContent(lista);	
			}			
		}
		
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/listar-paginado")
	public ResponseEntity<?> listarPaginado(
			@RequestParam(value = "idEmpresa", required = true) long idEmpresa,
			@RequestParam(value = "idSucursal", required = false, defaultValue = "0") Long idSucursal,
			@RequestParam(value = "idProducto", required = false, defaultValue = "0") Long idProducto,
			@RequestParam(value = "codigosTipoProducto", required = false) String codigosTipoProducto,
			@RequestParam(value = "idsCategorias", required = false) String idsCategorias,
			@RequestParam(value = "cantidadItems", required = false, defaultValue = "0") Integer cantidadItems,
			@RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
			@RequestParam(value = "filtro", required = false, defaultValue = "") String filtro,
			@RequestParam(value = "campoOrden", required = false, defaultValue = "") String campoOrden,
			@RequestParam(value = "tipoOrden", required = false, defaultValue = "1") Integer tipoOrden,
			@RequestParam(value = "campoEspecifico", required = false) String campoEspecifico
			) {
		SolicitudListaPaginadaProductos solicitud = SolicitudListaPaginadaProductos.builder().idEmpresa(idEmpresa).cantidadItems(cantidadItems).filtro(filtro)
				.campoOrden(campoOrden).tipoOrden(tipoOrden)
				.idsCategorias(idsCategorias)
				.idSucursal(idSucursal).codigosTipoProducto(codigosTipoProducto)
				.campoEspecifico(campoEspecifico).idProducto(idProducto)
				.pagina(pagina).build();
		RespuestaRest respuesta = servicio.listarPaginado(solicitud);
		// obtener datos adicionales
		if (respuesta.isSuccess() && respuesta.getContent()!=null) {
			RespuestaListaPaginada paginada = (RespuestaListaPaginada)respuesta.getContent();
			@SuppressWarnings("unchecked")
			List<ProductoDto> lista = (List<ProductoDto>) paginada.getItems();
			servicio.cargarDatosAdicionales(lista, idSucursal);
			paginada.setItems(lista);
			respuesta.setContent(paginada);
		}
		
		return new ResponseEntity<>(respuesta, respuesta.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
