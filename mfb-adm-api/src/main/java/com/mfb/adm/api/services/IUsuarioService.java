package com.mfb.adm.api.services;

import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.dtos.UsuarioRegistroClienteDto;
import com.mfb.adm.comm.dtos.UsuarioRegistroDto;
import com.mfb.adm.comm.requests.SolicitudBusquedaUsuarios;

public interface IUsuarioService {

	RespuestaRest actualizarPassword(String username, String password, String user);

	RespuestaRest registrar(UsuarioRegistroDto dto);
	
	RespuestaRest registrarCliente(UsuarioRegistroClienteDto dto);

	RespuestaRest actualizar(UsuarioRegistroDto dto);

	RespuestaRest eliminar(Long id);

	RespuestaRest verPorId(Long id);
	
	RespuestaRest listarPorCriterios(SolicitudBusquedaUsuarios criterios);

	RespuestaRest obtenerSession(String username);
		
	RespuestaRest listarOpciones();

}
