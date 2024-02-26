package com.mfb.adm.api.services;

import java.util.List;

import com.mfb.adm.comm.dtos.ProductoDto;
import com.mfb.adm.comm.dtos.ProductoResumenDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaProductos;
import com.mfb.adm.comm.requests.SolicitudCargaMasivaProductos;
import com.mfb.adm.comm.requests.SolicitudListaPaginadaProductos;

public interface IProductoService {

	RespuestaRest registrar(ProductoDto dto);

	RespuestaRest actualizar(ProductoDto dto);

	RespuestaRest eliminar(Long id);

	RespuestaRest verPorId(Long id);

	RespuestaRest listarPorCriterios(SolicitudBusquedaProductos criterios);
	
	RespuestaRest listarPaginado(SolicitudListaPaginadaProductos criterios);
	
	RespuestaRest registrarMasivo(SolicitudCargaMasivaProductos solicitud);
	
	void cargarDatosAdicionales(List<ProductoDto> dto, Long idSucursal);
	
	void cargarDatosAdicionales(ProductoDto dto, Long idSucursal);
	
	void cargarResumenDatosAdicionales(List<ProductoResumenDto> dto, Long idSucursal);

}
