package com.mfb.adm.core.query;

import java.util.List;

import com.mfb.adm.comm.dtos.ProductoDto;
import com.mfb.adm.comm.dtos.ProductoResumenDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.requests.SolicitudBusquedaProductos;
import com.mfb.adm.comm.requests.SolicitudListaPaginadaProductos;

public interface IProductoQuery {

	public ProductoDto verPorId(Long id);

	public List<ProductoDto> listarPorCriterios(SolicitudBusquedaProductos criterios);
	
	public List<ProductoResumenDto> listarResumenPorCriterios(SolicitudBusquedaProductos criterios);
	
	public RespuestaListaPaginada listarPaginado(SolicitudListaPaginadaProductos criterios);

}
