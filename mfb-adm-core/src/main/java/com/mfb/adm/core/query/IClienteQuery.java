package com.mfb.adm.core.query;

import java.util.List;

import com.mfb.adm.comm.dtos.ClienteDto;
import com.mfb.adm.comm.dtos.ClienteResumenDto;
import com.mfb.adm.comm.dtos.RespuestaListaPaginada;
import com.mfb.adm.comm.requests.SolicitudBusquedaClientes;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;

public interface IClienteQuery {

	public ClienteDto verPorId(Long id);
	
	public String verTelefonoPorIdEmpresaYCodigo(Long idEmpresa, String codigoCliente);

	public List<ClienteDto> listarPorCriterios(SolicitudBusquedaClientes criterios);
	
	public List<ClienteResumenDto> listarResumenPorCriterios(SolicitudBusquedaClientes criterios);
	
	public RespuestaListaPaginada listarPaginado(SolicitudListaPaginada criterios);

}
