package com.mfb.adm.api.services;

import com.mfb.adm.comm.dtos.EmpresaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;

public interface IEmpresaService {

	RespuestaRest registrar(EmpresaDto dto);

	RespuestaRest actualizar(EmpresaDto dto);

	RespuestaRest eliminar(Long id);

	RespuestaRest verPorId(Long id);
	
	RespuestaRest verPorNit(Long nit);

	RespuestaRest listar();
	
}
