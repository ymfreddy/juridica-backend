package com.mfb.adm.api.services;

import com.mfb.adm.comm.dtos.ClienteDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaClientes;
import com.mfb.adm.comm.requests.SolicitudListaPaginada;

public interface IClienteService {

	RespuestaRest registrar(ClienteDto dto);

	RespuestaRest actualizar(ClienteDto dto);

	RespuestaRest eliminar(Long id);

	RespuestaRest verPorId(Long id);
	
	RespuestaRest listarPorCriterios(SolicitudBusquedaClientes criterios);
	
	RespuestaRest listarPaginado(SolicitudListaPaginada criterios);

	RespuestaRest verTelefonoPorIdEmpresaYCodigo(Long idEmpresa, String codigoCliente);
}
