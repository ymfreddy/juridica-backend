package com.mfb.adm.api.services;

import com.mfb.adm.comm.dtos.CategoriaDto;
import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudBusquedaCategorias;

public interface ICategoriaService {

	RespuestaRest registrar(CategoriaDto dto);

	RespuestaRest actualizar(CategoriaDto dto);

	RespuestaRest eliminar(Long id);

	RespuestaRest verPorId(Long id);

	RespuestaRest listarPorCriterios(SolicitudBusquedaCategorias criterios);

}
